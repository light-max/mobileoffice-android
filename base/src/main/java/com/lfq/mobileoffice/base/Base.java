package com.lfq.mobileoffice.base;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.lfq.mobileoffice.base.interfaces.KeyValue;
import com.lfq.mobileoffice.base.interfaces.ViewGet;

/**
 * Base接口
 */
public interface Base extends KeyValue, ViewGet {
    /**
     * 获取继承了{@link Base}接口的{@link Context}对象<br>
     * {@link androidx.fragment.app.Fragment}已经实现了此方法
     */
    default Context getContext() {
        // 如果子类本身就是Context的子类就返回this
        if (this instanceof Context) {
            return (Context) this;
        }
        return null;
    }

    /**
     * 弹出toast<br>
     * 对{@link Toast#makeText(Context, int, int)}的包装
     */
    default void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度对话框
     */
    default void progress(String message) {
        progress(message, false);
    }

    /**
     * 显示进度对话框
     */
    void progress(String message, boolean cancelable);

    /**
     * 关闭对话框
     */
    void dismissDialog();

    /**
     * 获取ViewModel对象，没有会创建
     */
    <T extends ViewModel> T getModel(Class<T> tClass);

    /**
     * 启动activity
     */
    void startActivity(Class<?> tClass);
}
