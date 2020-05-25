package com.chinare.opension2.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinare.opension2.common.service.CacheKeyGenerator;
import com.chinare.opension2.common.service.impl.LockKeyGenerator;

/**
 * @author demo 1992lcg@163.com
 */
@Configuration
public class LockConfig {
	@Bean
	public CacheKeyGenerator cacheKeyGenerator() {
		return  new LockKeyGenerator();
	}

}
