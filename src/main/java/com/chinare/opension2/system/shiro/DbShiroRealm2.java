package com.chinare.opension2.system.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.chinare.opension2.common.config.ApplicationContextRegister;
import com.chinare.opension2.system.dao.UserDao;
import com.chinare.opension2.system.domain.UserDO;
import com.chinare.opension2.system.service.MenuService;

public class DbShiroRealm2 extends AuthorizingRealm {
	
	@Value("${generator.authServer}")
	private String authServer;
	
	@Autowired
	UserDao userMapper;
	
	public DbShiroRealm2() {
		 this.setCredentialsMatcher(new DbCredentialsMatcher());
	}
	@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {      
		UserDO user= (UserDO) principals.getPrimaryPrincipal();
		MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
		//改成根据name查询 bug
		Set<String> perms = menuService.listPerms(user.getUserId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
			String username = (String) token.getPrincipal();
			Map<String, Object> params = new HashMap<>(16);
			params.put("username", username);
			String password = new String((char[]) token.getCredentials());
			UserDO user = new UserDO();
			user.setUsername(username);
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
			return info;
	}
}
