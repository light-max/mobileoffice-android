package com.lfq.mobileoffice.ui.home.message;

import android.os.Bundle;
import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.base.BaseFragment;

/**
 * 实现了{@link androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener}接口的fragment,<br>
 * 继承了{@link com.lfq.mobileoffice.base.BaseFragment}
 *
 * @author 李凤强
 */
public abstract class RefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    /**
     * 下拉刷新回调方法.<br>
     * {@link #onViewCreated(View, Bundle)}方法中也会初始化一次
     */
    @Override
    public abstract void onRefresh();
}
