package com.chinare.opension2.system.shiro;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chinare.opension2.common.config.ApplicationContextRegister;
import com.chinare.opension2.system.dao.UserDao;
import com.chinare.opension2.system.domain.UserDO;
import com.chinare.opension2.system.service.AuthCenterService;
import com.chinare.opension2.system.service.UserService;
@Component
public class JwtUtils {
	
	
	@Value("${generator.authServer}")
	private  String server;
	
	@Value("${spring.application.name}")
	private  String eglName;
	
	@Autowired
	static UserDao userMapper;
	
	private static String authServer;
	
	private static String applEglName;
	
	@PostConstruct
	public void setStaticAttribute(){
		authServer=this.server;
		applEglName=this.eglName;
	}
	
	
	
	
	/**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,expireTime后过期
     * @param username 用户名
     * @param time 过期时间s
     * @return 加密的token
     */
    public static String sign(String username, String salt, long time) {
        Date date = new Date(System.currentTimeMillis()+time*1000);
		Algorithm algorithm = Algorithm.HMAC256(salt);
		// 附带username信息
		return JWT.create()
		        .withClaim("username", username)
		        .withExpiresAt(date)
		        .withIssuedAt(new Date())
		        .sign(algorithm);
    }

    /**
     * token是否过期
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 生成随机盐,长度32位
     * @return
     */
    public static String generateSalt(){
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(16).toHex();
        return hex;
    }
    
    /**
     * 保存user登录信息，返回token
     * @param userDto
     */
    public static String generateJwtToken(String username) {
    	String salt = "12345";//JwtUtils.generateSalt();
    	/**
    	 * @todo 将salt保存到数据库或者缓存中
    	 * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
    	 */   	
    	return JwtUtils.sign(username, salt, 3600); //生成jwt token，设置过期时间为1小时
    }
    
    /**
     * 获取上次token生成时的salt值和登录用户信息
     * @param username
     * @return
     */
    public static UserDO getJwtTokenInfo(String username) {
    	 String salt = "12345";
    	/**
    	 * @todo 从数据库或者缓存中取出jwt token生成时用的salt
    	 * salt = redisTemplate.opsForValue().get("token:"+username);
    	 */   	
    	 UserDO user = getUserInfo(username);
    	 user.setSalt(salt);
    	return user;
    }
    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码
     * @param userName
     * @return
     */
    public static UserDO getUserInfo(String userName) {
//    	HashMap<String,Object> map = new HashMap<String,Object>();
//    	map.put("userID", 1L);
//    	map.put("userName", "admin");
//    	map.put("password", new Sha256Hash("111111", encryptSalt).toHex());
//    	return map;
    		Map<String, Object> params = new HashMap<>(16);
    		String app_name = applEglName.replace("_server", "").trim();
    		params.put("username", userName);
    		params.put("app_name", app_name);
    		UserDO user = null;
		if("AUTH-CENTER".equals(authServer)) {
			AuthCenterService authCenterService = ApplicationContextRegister.getBean(AuthCenterService.class);
			user = authCenterService.getUserByUserName(params);
		}else {
			UserService userService = ApplicationContextRegister.getBean(UserService.class);
			user = userService.getUserByUserName(params);
		}
		return user;
    }
}
