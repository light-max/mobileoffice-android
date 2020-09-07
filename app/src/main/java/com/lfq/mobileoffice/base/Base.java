package com.lfq.mobileoffice.base;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;

import java.util.Map;
import java.util.Objects;

/**
 * Base接口
 */
public interface Base {
    /**
     * 根据id获取视图<br>
     * 对{@link View#findViewById(int)}的包装<br>
     * <p>问：为什么要用这种方式写代码？不是多此一举吗？。答：降低代码查重率</p>
     */
    <T extends View> T get(@IdRes int viewId);

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
     * 获取{@link TextView}<br>
     * 对{@link #get(int)}进行包装，把返回值转换为{@link TextView}
     */
    default TextView text(@IdRes int textViewId) {
        return get(textViewId);
    }

    /**
     * 获取视图中的{@link SwipeRefreshLayout}控件<br>
     * 需要在控件中设置id{@link R.id#swipe}
     */
    default SwipeRefreshLayout swipe() {
        SwipeRefreshLayout swipeRefreshLayout = get(R.id.swipe);
        Objects.requireNonNull(swipeRefreshLayout, "没有找到[@id/swipe]控件");
        return swipeRefreshLayout;
    }

    /**
     * 为视图绑定点击事件<br>
     * 对{@link View#setOnClickListener(View.OnClickListener)}的包装
     */
    default void click(@IdRes int viewId, View.OnClickListener listener) {
        get(viewId).setOnClickListener(listener);
    }

    /**
     * 为视图绑定点击事件<br>
     * 对{@link View#setOnClickListener(View.OnClickListener)}的包装
     */
    default void click(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    /**
     * 为{@link CompoundButton}视图绑定{@link CompoundButton.OnCheckedChangeListener}事件<br>
     * 对{@link CompoundButton#setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener)}的包装
     */
    default void checkChange(@IdRes int viewId, CompoundButton.OnCheckedChangeListener listener) {
        ((CompoundButton) get(viewId)).setOnCheckedChangeListener(listener);
    }

    /**
     * 为{@link CompoundButton}视图绑定{@link CompoundButton.OnCheckedChangeListener}事件<br>
     * 对{@link CompoundButton#setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener)}的包装
     */
    default void checkChange(CompoundButton compoundButton, CompoundButton.OnCheckedChangeListener listener) {
        compoundButton.setOnCheckedChangeListener(listener);
    }

    /**
     * 为{@link TextView}视图设置文本<br>
     * 对{@link TextView#setText(CharSequence)}进行包装
     */
    default void text(@IdRes int textViewId, String text) {
        ((TextView) get(textViewId)).setText(text);
    }

    /**
     * 为{@link SwipeRefreshLayout}设置{@link SwipeRefreshLayout.OnRefreshListener}监听事件<br>
     * 对{@link SwipeRefreshLayout#setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener)}的包装<br>
     * {@link SwipeRefreshLayout}来源{@link #swipe()}
     */
    default void swipe(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipe().setOnRefreshListener(onRefreshListener);
    }

    /**
     * 为{@link SwipeRefreshLayout}设置新的刷新状态<br>
     * 对{@link SwipeRefreshLayout#setRefreshing(boolean)}的包装
     * {@link SwipeRefreshLayout}来源{@link #swipe()}
     */
    default void swipe(boolean refreshing) {
        swipe().setRefreshing(refreshing);
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
     * @see Base#map()
     */
    default <T> T map(Object key) {
        return (T) map().get(key);
    }

    /**
     * 把键和值put进map中
     *
     * @param key   键
     * @param value 值
     * @see Base#map()
     */
    default <T> T map(Object key, Object value) {
        return (T) map().put(key, value);
    }
}
