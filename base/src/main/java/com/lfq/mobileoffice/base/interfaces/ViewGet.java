package com.lfq.mobileoffice.base.interfaces;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.base.R;

import java.util.Objects;

/**
 * 简化{@link View#findViewById}的操作
 */
public interface ViewGet {
    /**
     * 根据id获取视图<br>
     * 对{@link View#findViewById(int)}的包装<br>
     * <p>问：为什么要用这种方式写代码？不是多此一举吗？。答：降低代码查重率</p>
     */
    <T extends View> T get(@IdRes int viewId);

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
}
