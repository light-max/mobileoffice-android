package com.lfq.mobileoffice.ui.resources;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Resource;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.ui.home.message.leave.AdapterMeLeave;

/**
 * 资源文件列表适配器
 */
class ResourceListAdapter extends SimpleRecyclerAdapter<Resource, ResourceListAdapter.ViewHolder> {

    private OnDownloadListener onDownloadListener;
    private OnDeleteListener onDeleteListener;

    public ResourceListAdapter(Base base) {
        super(base);
    }

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_file_2;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Resource data) {
        if (data.getType().contains("image")) {
            Glide.with(holder.icon)
                    .load(Api.url("resource/" + data.getId()))
                    .into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }
        holder.name.setText(data.getName());
        if (Util.getFile(data, false).exists()) {
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.delete.setVisibility(View.GONE);
        }
        holder.download.setOnClickListener(v -> {
            if (onDownloadListener != null) {
                onDownloadListener.onDownload(holder.delete, data);
            }
        });
        holder.delete.setOnClickListener(v -> {
            if (onDeleteListener != null) {
                onDeleteListener.onDelete(holder.delete, data);
            }
        });
    }

    public static class ViewHolder extends AdapterMeLeave.ViewHolder {

        private final ImageView icon;
        private final TextView name;
        private final TextView download;
        private final TextView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            download = itemView.findViewById(R.id.download);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    /**
     * 下载按钮接口
     */
    public interface OnDownloadListener {
        /**
         * 当点击item中的下载按钮时就会调用这个函数
         *
         * @param deleteButton 删除按钮, 用于下载后显示删除按钮
         * @param resource     当前点击的资源
         */
        void onDownload(TextView deleteButton, Resource resource);
    }

    /**
     * 删除本地文件按钮接口
     */
    public interface OnDeleteListener {
        /**
         * 当点击item中的删除按钮时就会调用这个函数
         *
         * @param deleteButton 删除按钮
         * @param resource     当前点击的资源
         */
        void onDelete(TextView deleteButton, Resource resource);
    }
}
