package com.chinare.opension2.system.shiro;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinare.opension2.common.utils.R;
import com.chinare.opension2.system.domain.UserDO;

public class JwtAuthFilter extends AuthenticatingFilter {

	private static final int tokenRefreshInterval = 300;
	private final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

	public JwtAuthFilter() {
		this.setLoginUrl("/login");
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) // 对于OPTION请求做拦截，不做token校验
			return false;
		
		return super.preHandle(request, response);
	}

	@Override
	protected void postHandle(ServletRequest request, ServletResponse response) {
		this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
		request.setAttribute("jwtShiroFilter.FILTERED", true);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (this.isLoginRequest(request, response))
			return true;
		Boolean afterFiltered = (Boolean) (request.getAttribute("jwtShiroFilter.FILTERED"));
		if (BooleanUtils.isTrue(afterFiltered)) {
			return true;
		}

		boolean allowed = false;
		try {
			allowed = executeLogin(request, response);
		} catch (IllegalStateException e) { // not found any token
			log.error("Not found any token or token has Expired");
			HttpServletResponse httpResponse = WebUtils.toHttp(response);
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.setStatus(403);
			fillCorsHeader(WebUtils.toHttp(request), httpResponse);
			try {
				httpResponse.getWriter().write(R.error(403, "没有token或token已过期，请重新登录").toString());
				Cookie cookie = new Cookie("jwttoken", null);
				cookie.setMaxAge(0);
				httpResponse.addCookie(cookie);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			log.error("Error occurs when login", e);
			e.printStackTrace();
		}
		return allowed || super.isPermissive(mappedValue);
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
		String jwtToken = getAuthzHeader(servletRequest);
		if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken))
			return new JWTToken(jwtToken);
		return null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json;charset=UTF-8");
		httpResponse.setStatus(403);
		fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
		return false;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		String newToken = null;
		if (token instanceof JWTToken) {
			JWTToken jwtToken = (JWTToken) token;
			UserDO user = (UserDO) subject.getPrincipal();

			boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
			if (shouldRefresh) {
				newToken = JwtUtils.generateJwtToken(user.getUsername());
			}
		}
		if (StringUtils.isNotBlank(newToken)) {
			// httpResponse.setHeader("jwttoken", newToken);
			httpResponse.addCookie(new Cookie("jwttoken", newToken));
		}
		return true;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
		return false;
	}

	protected String getAuthzHeader(ServletRequest request) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		// String header = httpRequest.getHeader("jwttoken");
		// return StringUtils.removeStart(header, "Bearer ");
		Cookie[] cookies = httpRequest.getCookies();
		String returnStr = "";
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals("jwttoken")) {
					String token = c.getValue();
					if(token!=null&&token.length()>10) {
						returnStr = c.getValue();
					}
				}
			}
		}
		return returnStr;
	}

	protected boolean shouldTokenRefresh(Date issueAt) {
		LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
		return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
	}

	protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
	}
}
