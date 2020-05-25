package com.chinare.opension2.common.service;

import com.chinare.opension2.common.domain.LogDO;
import com.chinare.opension2.common.domain.PageDO;
import com.chinare.opension2.common.utils.Query;
import org.springframework.stereotype.Service;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
