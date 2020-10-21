package com.lfq.mobileoffice.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志级别注解，加上这个注解代表要打印{@link Logger#error}级别的注解
 *
 * @author 李凤强
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface LoggerError {
}
