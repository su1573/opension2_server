package com.chinare.opension2.biz.sysmanage.service;

import com.chinare.opension2.biz.sysmanage.domain.OpSysUserDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:57
 */
public interface OpSysUserService {
	
	OpSysUserDO get(String operator);
	
	List<OpSysUserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OpSysUserDO opSysUser);
	
	int update(OpSysUserDO opSysUser);
	
	int remove(Map<String, Object> params);
	
	int batchRemove(String[] operators);
}
