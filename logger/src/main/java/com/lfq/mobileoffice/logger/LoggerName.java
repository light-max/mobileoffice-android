package com.lfq.mobileoffice.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志tag注解
 *
 * @author 李凤强
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggerName {

    /**
     * 日志名
     */
    String value();

    /**
     * 是否启用
     */
    boolean enable() default true;
}
