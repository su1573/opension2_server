package com.chinare.opension2.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chinare.opension2.common.annotation.FormRepeatSubmit;
import com.chinare.opension2.common.service.CacheKeyGenerator;

import java.lang.reflect.Method;

/**
 * redis 方案
 */
@Aspect
@Component
public class LockMethodInterceptor {

	@Autowired
	public LockMethodInterceptor(StringRedisTemplate lockRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {

		this.lockRedisTemplate = lockRedisTemplate;
		this.cacheKeyGenerator = cacheKeyGenerator;
	}

	private final StringRedisTemplate lockRedisTemplate;
	private final CacheKeyGenerator cacheKeyGenerator;
	
    @Pointcut("@annotation(com.chinare.opension2.common.annotation.FormRepeatSubmit)")
    public void pointcut() {
    }

	
	@Around(value="pointcut()")
	public Object interceptor(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		FormRepeatSubmit lock = method.getAnnotation(FormRepeatSubmit.class);
		if (StringUtils.isEmpty(lock.prefix())) {
			throw new RuntimeException("lock key can't be null...");
		}
		final String lockKey = cacheKeyGenerator.getLockKey(pjp);
		System.out.println(lockKey+"............................");
		try {
			// key不存在才能设置成功
			final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "");
			if (success) {
				lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
			} else {
				// 按理来说 我们应该抛出一个自定义的 CacheLockException 异常;
				throw new RuntimeException("请勿重复请求");
			}
			try {
				return pjp.proceed();
			} catch (Throwable throwable) {
				throw new RuntimeException("系统异常");
			}
		} finally {
			// 如果演示的话需要注释该代码;实际应该放开
			 lockRedisTemplate.delete(lockKey);
		}
	}
}
