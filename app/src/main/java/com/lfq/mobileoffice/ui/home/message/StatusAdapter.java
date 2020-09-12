package com.lfq.mobileoffice.ui.home.message;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link SimpleRecyclerAdapter}封装，主要功能是为了统一处理
 * <b>{@link #dateFormat},{@link #status},{@link #base}</b>
 *
 * @see SimpleRecyclerAdapter
 */
public abstract class StatusAdapter<T, VH extends RecyclerView.ViewHolder>
        extends SimpleRecyclerAdapter<T, VH>
        implements SimpleRecyclerAdapter.OnItemClickListener<T> {

    protected final String dateFormat = "YYYY/MM/dd HH:mm";
    protected final Map<Integer, Pair<Integer, String>> status;
    protected Base base;

    public StatusAdapter(Base base) {
        super(base);
        this.base = base;
        status = new HashMap<Integer, Pair<Integer, String>>() {{
            put(1, new Pair<>(base.getContext().getColor(R.color.colorAccent), "待受理"));
            put(2, new Pair<>(base.getContext().getColor(R.color.colorSafe), "已同意"));
            put(3, new Pair<>(base.getContext().getColor(R.color.colorDanger), "被拒绝"));
        }};
        setOnItemClickListener(this);
    }
}
