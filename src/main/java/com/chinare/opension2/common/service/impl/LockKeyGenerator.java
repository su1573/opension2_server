package com.chinare.opension2.common.service.impl;

import com.chinare.opension2.common.annotation.CacheParam;
import com.chinare.opension2.common.annotation.FormRepeatSubmit;
import com.chinare.opension2.common.config.GeneratorConfig;
import com.chinare.opension2.common.dao.FileDao;
import com.chinare.opension2.common.domain.FileDO;
import com.chinare.opension2.common.service.CacheKeyGenerator;
import com.chinare.opension2.common.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 通过接口注入的方式去写不同的生成规则;
 */
@Service
public class LockKeyGenerator implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        FormRepeatSubmit lockAnnotation = method.getAnnotation(FormRepeatSubmit.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();
        //默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null) {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtils.isEmpty(builder.toString())) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (annotation == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }
        return lockAnnotation.prefix() + builder.toString();
    }
}
