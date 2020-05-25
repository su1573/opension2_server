package com.chinare.opension2.biz.sysmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinare.opension2.biz.sysmanage.dao.OpSysCodeDao;
import com.chinare.opension2.biz.sysmanage.domain.OpSysCodeDO;
import com.chinare.opension2.biz.sysmanage.service.OpSysCodeService;



@Service
public class OpSysCodeServiceImpl implements OpSysCodeService {
	@Autowired
	private OpSysCodeDao opSysCodeDao;
	
	@Override
	public OpSysCodeDO get(Date lastoptdate){
		return opSysCodeDao.get(lastoptdate);
	}
	
	@Override
	public List<OpSysCodeDO> list(Map<String, Object> map){
		return opSysCodeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return opSysCodeDao.count(map);
	}
	
	@Override
	public int save(OpSysCodeDO opSysCode){
		return opSysCodeDao.save(opSysCode);
	}
	
	@Override
	public int update(OpSysCodeDO opSysCode){
		return opSysCodeDao.update(opSysCode);
	}
	
	@Override
	public int remove(Map<String, Object> params){
		return opSysCodeDao.remove(params);
	}
	
	@Override
	public int batchRemove(Date[] lastoptdates){
		return opSysCodeDao.batchRemove(lastoptdates);
	}
	
}
