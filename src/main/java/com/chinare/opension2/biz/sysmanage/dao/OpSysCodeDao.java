package com.chinare.opension2.biz.sysmanage.dao;

import com.chinare.opension2.biz.sysmanage.domain.OpSysCodeDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 


 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:56
 */
@Mapper
public interface OpSysCodeDao {

	OpSysCodeDO get(Date lastoptdate);
	
	List<OpSysCodeDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(OpSysCodeDO opSysCode);
	
	int update(OpSysCodeDO opSysCode);
	
	int remove(Map<String, Object> params);
	
	int batchRemove(Date[] lastoptdates);
}
