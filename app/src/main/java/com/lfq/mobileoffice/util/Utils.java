package com.lfq.mobileoffice.util;

import android.annotation.SuppressLint;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常用工具类
 */
public class Utils {
    /**
     * 把对象转换为字符串
     */
    public static String string(Object o) {
        if (o instanceof TextView) {
            return ((TextView) o).getText().toString();
        }
        return o.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateFormat(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time));
    }
}
