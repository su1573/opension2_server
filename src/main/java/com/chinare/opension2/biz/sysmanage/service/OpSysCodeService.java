package com.chinare.opension2.biz.sysmanage.service;

import com.chinare.opension2.biz.sysmanage.domain.OpSysCodeDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 


 * 
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:56
 */
public interface OpSysCodeService {
	
	OpSysCodeDO get(Date lastoptdate);
	
	List<OpSysCodeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OpSysCodeDO opSysCode);
	
	int update(OpSysCodeDO opSysCode);
	
	int remove(Map<String, Object> params);
	
	int batchRemove(Date[] lastoptdates);
}
