package com.lfq.mobileoffice.base.interfaces;

import java.util.Map;

/**
 * 键值对操作接口
 *
 * @see #map()
 */
public interface KeyValue {
    /**
     * 返回一个作用域为类内部的键值对对象<br>
     * 用于存储一些作用域为整个类的对象<br>
     * 比如子类中发送网络请求需要保存一个对象，如果不想新建一个变量就可以存储在这个map对象中
     */
    Map<Object, Object> map();

    /**
     * 根据键获取map中的值
     *
     * @param key 键
     * @see #map()
     */
    default <T> T map(Object key) {
        return (T) map().get(key);
    }

    /**
     * 把键和值put进map中
     *
     * @param key   键
     * @param value 值
     * @see #map()
     */
    default <T> T map(Object key, Object value) {
        return (T) map().put(key, value);
    }
}
