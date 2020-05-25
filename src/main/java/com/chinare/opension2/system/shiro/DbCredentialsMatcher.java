package com.chinare.opension2.system.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.nutz.lang.Lang;

/**
 * @author 王贵源<wangguiyuan@sinosoft.com.cn>
 *
 * @date 2018-05-04 16:47:01
 *
 *
 */
public class DbCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		return equals(token.getPassword(), getCredentials(info));
	}

	/**
	 * 加密
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return 密码密文
	 */
	public static String password(char[] password, String userName) {
		return password(userName, new String(password));
	}
	
	/**
	 * 加密
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return 密码密文
	 */
	public static String password(String userName, String password) {
		return Lang.md5(password).toUpperCase();
	}

}
