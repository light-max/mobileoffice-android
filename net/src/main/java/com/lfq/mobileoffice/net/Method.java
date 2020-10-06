package com.lfq.mobileoffice.net;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求方式注解
 * 在方法的参数中使用
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@StringDef({Net.GET, Net.POST, Net.PUT, Net.DELETE})
@interface Method {
}
