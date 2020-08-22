package com.lfq.mobileoffice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.listener.OnLoadMoreListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 简易RecyclerView适配器<br>
 * 只适用于一种类型的item
 *
 * @param <T>  item的数据类型
 * @param <VH> 继承于{@link RecyclerView.ViewHolder}
 */
public abstract class SimpleRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected LayoutInflater inflater;
    protected List<T> data = new ArrayList<>();
    protected OnItemClickListener<T> onItemClickListener;
    protected OnItemLongClickListener<T> onItemLongClickListener;
    /*** 适配器绑定的视图 */
    protected RecyclerView view;
    /*** VH 类的构造器*/
    private Constructor<VH> vhConstructor;

    /**
     * 创建base所在界面的默认{@link RecyclerView}的适配器<br>
     * 默认id为{@link R.id#recycler}
     *
     * @param base 需要base对象
     * @see #SimpleRecyclerAdapter(Base, int)
     */
    public SimpleRecyclerAdapter(Base base) {
        this(base, R.id.recycler);
    }

    /**
     * 创建base所在页面id为recyclerViewId的{@link RecyclerView}的适配器<br>
     *
     * @param base           需要base来查找控件
     * @param recyclerViewId {@link RecyclerView}控件的id
     * @see #SimpleRecyclerAdapter(Base)
     */
    public SimpleRecyclerAdapter(Base base, @IdRes int recyclerViewId) {
        view = base.get(recyclerViewId);
        view.addItemDecoration(new DividerItemDecoration(base.getContext(), DividerItemDecoration.VERTICAL));
        view.setAdapter(this);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return makerViewHolder(inflater.inflate(getItemResource(), parent, false));
    }

    /**
     * 创建VH实例对象
     */
    private VH makerViewHolder(View view) {
        try {
            if (vhConstructor == null) {
                ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
                assert superclass != null;
                Class<VH> vhClass = (Class<VH>) superclass.getActualTypeArguments()[1];
                vhConstructor = vhClass.getConstructor(View.class);
            }
            return vhConstructor.newInstance(view);
        } catch (Exception e) {
            throw new RuntimeException("无法实例化" + vhConstructor);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(data.get(position), position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(data.get(position), position);
            }
            return true;
        });
        onBindViewHolder(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * @return item布局资源
     */
    protected abstract int getItemResource();

    /**
     * 绑定视图和数据的时候调用
     *
     * @param holder 视图holder
     * @param data   这一个holder对应的数据
     */
    protected abstract void onBindViewHolder(VH holder, T data);

    /**
     * 获取适配器的数据
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置适配器的数据源
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按事件
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置上拉加载更多回调事件
     *
     * @see com.lfq.mobileoffice.listener.OnLoadMoreListener
     */
    public void setOnLoadMoreListener(Runnable runnable) {
        this.view.addOnScrollListener(new OnLoadMoreListener(runnable));
    }

    /**
     * 获取适配器当前绑定到的{@link RecyclerView}
     */
    public RecyclerView getView() {
        return view;
    }

    /**
     * 把适配器绑定到{@link RecyclerView}视图
     */
    public void withView(RecyclerView view) {
        this.view = view;
        view.setAdapter(this);
    }

    /**
     * 每一项item的单击事件接口
     *
     * @param <T> 数据类型
     */
    public interface OnItemClickListener<T> {
        /**
         * 当item被单击的回调
         *
         * @param data     数据
         * @param position 下标
         */
        void onItemClick(T data, int position);
    }

    /**
     * 每一项item的长按事件接口
     *
     * @param <T> 数据类型
     */
    public interface OnItemLongClickListener<T> {
        /**
         * 当item被长按的回调
         *
         * @param data     数据
         * @param position 下标
         */
        void onItemLongClick(T data, int position);
    }
}
