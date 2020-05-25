package com.chinare.opension2.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinare.opension2.common.annotation.Log;
import com.chinare.opension2.common.config.ApplicationContextRegister;
import com.chinare.opension2.common.config.GeneratorConfig;
import com.chinare.opension2.common.controller.BaseController;
import com.chinare.opension2.common.domain.FileDO;
import com.chinare.opension2.common.domain.Tree;
import com.chinare.opension2.common.service.FileService;
import com.chinare.opension2.common.utils.MD5Utils;
import com.chinare.opension2.common.utils.R;
import com.chinare.opension2.common.utils.ShiroUtils;
import com.chinare.opension2.system.domain.MenuDO;
import com.chinare.opension2.system.domain.UserDO;
import com.chinare.opension2.system.service.AuthCenterService;
import com.chinare.opension2.system.service.MenuService;
import com.chinare.opension2.system.shiro.JwtUtils;

import club.zhcs.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Value("${spring.application.name}")
	private  String eglName;
	
	
	
	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@Autowired
	GeneratorConfig generatorConfig;
	
	@Value("${spring.application.name}")
	private  String applEglName;
	
	@GetMapping({ "/", "" })
	String welcome(Model model) {

		return "redirect:/login";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if (fileDO != null && fileDO.getUrl() != null) {
			if (fileService.isExist(fileDO.getUrl())) {
				model.addAttribute("picUrl", fileDO.getUrl());
			} else {
				model.addAttribute("picUrl", "/img/photo_s.jpg");
			}
		} else {
			model.addAttribute("picUrl", "/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	// @GetMapping("/login")
	// String login(Model model) {
	// model.addAttribute("username", generatorConfig.getUsername());
	// model.addAttribute("password", generatorConfig.getPassword());
	// return "login";
	// }

	@Log("登录")
	@RequestMapping("/login")
	@ResponseBody
	R ajaxLogin(String userName, String passWord, HttpServletResponse response) {

		passWord = MD5Utils.encrypt(userName, passWord);
		UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			R r = R.ok();
			r.put("userName", userName);
			r.put("token", token);
			// Shiro认证通过后会将user信息放到subject内，生成token并返回
			UserDO user = (UserDO) subject.getPrincipal();
			String newToken = JwtUtils.generateJwtToken(user.getUsername());
			response.setHeader("jwttoken", newToken);
			response.addCookie(new Cookie("jwttoken", newToken));
			return r;
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@Log("登录统一认证")
	@RequestMapping("/loginCas")
	@ResponseBody
	R loginCas(String uri, Model model, HttpServletResponse response,HttpServletRequest request) {
		
		// 拿到ticket.
		String ticket = uri.substring(uri.indexOf("ticket") + 7);
		

		
		// 通过Feign远程调用auth_center.验证并获取jwttoken。
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("applEglName", applEglName);
		params.put("ticket", ticket);
		AuthCenterService authCenterService = ApplicationContextRegister.getBean(AuthCenterService.class);
		R result = authCenterService.getJwtToken(params);
		
		// 返回数据形式：
		if (result != null) {
			
			// 获取jwttoken
			String jwtToken = result.get("msg").toString();
			String username = JwtUtils.getUsername(jwtToken);
			R r = R.ok();
			r.put("userName", username);
			r.put("token", jwtToken);
			// Shiro认证通过后会将user信息放到subject内，生成token并返回
			response.setHeader("jwttoken", jwtToken);
			Cookie cookieName=new Cookie("jwttoken", jwtToken);
			cookieName.setPath("/");
			response.addCookie(cookieName);
			return r;
		} else {
			return R.error("认证失败，如果没有非法操作，请联系管理员！");
		}

	}
	
	
	@GetMapping("/logout")
	String logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {


		return "redirect:http://172.25.147.124:2222/logout";
	}

	
	@GetMapping("/logcas")
	String logcas(HttpSession session, HttpServletResponse response, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}

		return "redirect:http://172.25.147.124:2222/logindex";
	}
	
	
	@GetMapping("/main")
	String main() {
		return "main";
	}
	
	
	
}
