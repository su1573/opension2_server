package com.chinare.opension2.biz.sysmanage.dao;

import com.chinare.opension2.biz.sysmanage.domain.OpSysMenuDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author chinare
 * @email devops@chinare.com.cn
 * @date 2019-10-13 18:31:54
 */
@Mapper
public interface OpSysMenuDao {

	OpSysMenuDO get(String menuid);
	
	List<OpSysMenuDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(OpSysMenuDO opSysMenu);
	
	int update(OpSysMenuDO opSysMenu);
	
	int remove(Map<String, Object> params);
	
	int batchRemove(String[] menuids);
}
