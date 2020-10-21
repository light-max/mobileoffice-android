package com.lfq.mobileoffice.ui.home.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

/**
 * 消息记录fragment，包含了一个{@link androidx.recyclerview.widget.RecyclerView},
 * 视图与{@link android.widget.TextView}视图
 *
 * @author 李凤强
 */
public abstract class MessageRecyclerFragment<Adapter extends SimpleRecyclerAdapter<?, ?>> extends RefreshFragment {

    private Constructor<Adapter> adapterConstructor;
    protected Adapter adapter;

    @Override
    public int getViewResource() {
        return R.layout.fragment_message_recycler;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<Adapter> aClass = (Class<Adapter>) Objects.requireNonNull(superclass).getActualTypeArguments()[0];
        try {
            adapterConstructor = aClass.getConstructor(Base.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            adapter = adapterConstructor.newInstance(this);
            adapter.setOnLoadMoreListener(this::onLoadMore);
            onRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上拉加载更多回调方法，不需要可以不写
     */
    public void onLoadMore() {
    }

    /**
     * 是否显示{@link RecyclerView}
     *
     * @param show true: 显示{@link RecyclerView},隐藏{@link R.id#empty}文本提示
     */
    public void showRecyclerView(boolean show) {
        adapter.getView().setVisibility(show ? View.VISIBLE : View.GONE);
        View empty = get(R.id.empty);
        if (empty != null) {
            empty.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 设置数据
     */
    public void setData(List data) {
        if (!data.isEmpty()) {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
        showRecyclerView(!data.isEmpty());
    }

    /**
     * 添加数据
     */
    public void addData(List data) {
        adapter.getData().addAll(data);
        adapter.notifyDataSetChanged();
    }
}
