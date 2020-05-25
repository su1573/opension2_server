package com.chinare.opension2.biz.sysmanage.dao;

import com.chinare.opension2.biz.sysmanage.domain.OpSysUserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:57
 */
@Mapper
public interface OpSysUserDao {

	OpSysUserDO get(String operator);
	
	List<OpSysUserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(OpSysUserDO opSysUser);
	
	int update(OpSysUserDO opSysUser);
	
	int remove(Map<String, Object> params);
	
	int batchRemove(String[] operators);
}
