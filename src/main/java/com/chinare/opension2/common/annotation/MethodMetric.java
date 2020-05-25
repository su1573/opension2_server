package com.chinare.opension2.common.annotation;//sssssssssss

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodMetric {
	String name() default "";

    String description() default "";

    String[] tags() default {};
}
