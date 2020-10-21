package com.lfq.mobileoffice.logger;

import android.util.Log;

/**
 * 日志工具
 *
 * @author 李凤强
 */
@LoggerInfo
@LoggerError
public class Logger {
    private String tag;
    private boolean logI;
    private boolean logE;
    private boolean enable;

    public Logger(String tag) {
        this(tag, true);
    }

    public Logger(String tab, boolean enable) {
        this.tag = tab;
        this.enable = enable;
        logI = getClass().getAnnotation(LoggerInfo.class) != null;
        logE = getClass().getAnnotation(LoggerError.class) != null;
    }

    public static Logger getLogger(String TAG) {
        return new Logger(TAG);
    }

    public static Logger getLogger(String TAG, boolean enable) {
        return new Logger(TAG, enable);
    }

    public static Logger getLogger(Class<?> aClass) {
        LoggerName annotation = aClass.getAnnotation(LoggerName.class);
        if (annotation == null) {
            return new Logger(aClass.getName());
        } else {
            return new Logger(annotation.value(), annotation.enable());
        }
    }

    public static Logger getLogger(Object o) {
        return getLogger(o.getClass());
    }

    public void info(String msg) {
        if (enable && logI) Log.i(tag, msg);
    }

    public void error(String msg) {
        if (enable && logE) Log.e(tag, msg);
    }

    public void error(Throwable throwable) {
        if (enable && logE) Log.e(tag, "", throwable);
    }

    public void error(String msg, Throwable throwable) {
        if (enable && logE) Log.e(tag, msg, throwable);
    }
}
