package com.chinare.opension2.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinare.opension2.common.utils.R;
import com.chinare.opension2.system.domain.MenuDO;
import com.chinare.opension2.system.domain.UserDO;

@FeignClient("AUTH-CENTER")
public interface AuthCenterService {
	String auth_center_proxy_prefix="";
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/user/getUserByUserName", method = RequestMethod.GET)
	public UserDO getUserByUserName(@RequestParam("params") Map<String, Object> params);
	
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/user/getUserRoleListById", method = RequestMethod.GET)
	public UserDO getUserRoleListById(@RequestParam("params") Map<String, Object> params);
	
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/user/getMenuList", method = RequestMethod.GET)
	public List<MenuDO> getMenuList(@RequestParam("params") Map<String, Object> params);
	
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/user/getUserPermsOfAppl", method = RequestMethod.GET)
	public List<String> getUserPermsOfAppl(@RequestParam("params") Map<String, Object> params);
	
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/auth/getJwtToken", method = RequestMethod.GET)
	public R getJwtToken(@RequestParam("params") Map<String, Object> params);
	
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/auth/getJwtToken", method = RequestMethod.GET)
	public R logoutCas(Map<String, Object> params);
	
	@RequestMapping(value = auth_center_proxy_prefix+"/sys/user/listUserByRoleName", method = RequestMethod.GET)
	public List<UserDO> listUserByRoleName(@RequestParam Map<String, Object> params);
}
