package com.chinare.opension2.biz.sysmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chinare.opension2.biz.sysmanage.dao.OpSysMenuDao;
import com.chinare.opension2.biz.sysmanage.domain.OpSysMenuDO;
import com.chinare.opension2.biz.sysmanage.service.OpSysMenuService;



@Service
public class OpSysMenuServiceImpl implements OpSysMenuService {
	@Autowired
	private OpSysMenuDao opSysMenuDao;
	
	@Override
	public OpSysMenuDO get(String menuid){
		return opSysMenuDao.get(menuid);
	}
	
	@Override
	public List<OpSysMenuDO> list(Map<String, Object> map){
		return opSysMenuDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return opSysMenuDao.count(map);
	}
	
	@Override
	public int save(OpSysMenuDO opSysMenu){
		return opSysMenuDao.save(opSysMenu);
	}
	
	@Override
	public int update(OpSysMenuDO opSysMenu){
		return opSysMenuDao.update(opSysMenu);
	}
	
	@Override
	public int remove(Map<String, Object> params){
		return opSysMenuDao.remove(params);
	}
	
	@Override
	public int batchRemove(String[] menuids){
		return opSysMenuDao.batchRemove(menuids);
	}
	
}
