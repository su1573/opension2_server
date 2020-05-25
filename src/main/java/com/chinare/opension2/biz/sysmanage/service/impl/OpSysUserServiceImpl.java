package com.chinare.opension2.biz.sysmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chinare.opension2.biz.sysmanage.dao.OpSysUserDao;
import com.chinare.opension2.biz.sysmanage.domain.OpSysUserDO;
import com.chinare.opension2.biz.sysmanage.service.OpSysUserService;



@Service
public class OpSysUserServiceImpl implements OpSysUserService {
	@Autowired
	private OpSysUserDao opSysUserDao;
	
	@Override
	public OpSysUserDO get(String operator){
		return opSysUserDao.get(operator);
	}
	
	@Override
	public List<OpSysUserDO> list(Map<String, Object> map){
		return opSysUserDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return opSysUserDao.count(map);
	}
	
	@Override
	public int save(OpSysUserDO opSysUser){
		return opSysUserDao.save(opSysUser);
	}
	
	@Override
	public int update(OpSysUserDO opSysUser){
		return opSysUserDao.update(opSysUser);
	}
	
	@Override
	public int remove(Map<String, Object> params){
		return opSysUserDao.remove(params);
	}
	
	@Override
	public int batchRemove(String[] operators){
		return opSysUserDao.batchRemove(operators);
	}
	
}
