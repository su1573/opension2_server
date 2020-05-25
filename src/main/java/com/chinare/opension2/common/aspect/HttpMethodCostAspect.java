package com.chinare.opension2.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.chinare.opension2.common.annotation.MethodMetric;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Aspect
@Component
public class HttpMethodCostAspect {
	@Autowired
    private MeterRegistry meterRegistry;

    @Pointcut("@annotation(com.chinare.opension2.common.annotation.MethodMetric)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Method targetMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //这里是为了拿到实现类的注解
        Method currentMethod = ClassUtils.getUserClass(joinPoint.getTarget().getClass())
                .getDeclaredMethod(targetMethod.getName(), targetMethod.getParameterTypes());
        if (currentMethod.isAnnotationPresent(MethodMetric.class)) {
            MethodMetric methodMetric = currentMethod.getAnnotation(MethodMetric.class);
            return processMetric(joinPoint, currentMethod, methodMetric);
        } else {
            return joinPoint.proceed();
        }
    }

    private Object processMetric(ProceedingJoinPoint joinPoint, Method currentMethod,
                                 MethodMetric methodMetric) throws Throwable {
        String name = methodMetric.name();
        if (!StringUtils.hasText(name)) {
        	name=joinPoint.getTarget().getClass().getName();
            name = name+"."+currentMethod.getName();
        }
        String desc = methodMetric.description();
        if (!StringUtils.hasText(desc)) {
            desc = name;
        }
        String[] tags = methodMetric.tags();
        if (tags.length == 0) {
            tags = new String[2];
            tags[0] = "method";
            tags[1] = name;
        }
        Timer timer = Timer.builder("method_invoke").tags(tags)
                .description(desc)
                .register(meterRegistry);
        return timer.record(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new IllegalStateException(throwable);
            }
        });
    }
}
