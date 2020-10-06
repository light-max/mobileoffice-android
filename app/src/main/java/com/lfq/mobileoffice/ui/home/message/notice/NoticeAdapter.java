package com.lfq.mobileoffice.ui.home.message.notice;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Notice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 公告列表适配器
 */
class NoticeAdapter extends SimpleRecyclerAdapter<Notice, NoticeAdapter.ViewHolder> {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/M/d HH:mm") {{
        setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }};

    public NoticeAdapter(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_notice;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Notice data) {
        holder.title.setText(data.getTitle());
        holder.content.setText(data.getContent());
        holder.time.setText(dateFormat.format(new Date(data.getCreateTime())));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView content;
        private final TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }
    }
}
