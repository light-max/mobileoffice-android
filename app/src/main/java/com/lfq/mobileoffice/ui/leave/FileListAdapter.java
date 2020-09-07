package com.lfq.mobileoffice.ui.leave;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;

import java.io.File;

class FileListAdapter extends SimpleRecyclerAdapter<File, FileListAdapter.ViewHolder> {
    private OnDeleteListener onDeleteListener;

    public FileListAdapter(Base base) {
        super(base);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_file_1;
    }

    @Override
    protected void onBindViewHolder(FileListAdapter.ViewHolder holder, File data) {
        String name = data.getName();
        if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".bmp") ||
                name.endsWith(".gif") || name.endsWith(".png")) {
            Glide.with(holder.itemView)
                    .load(data)
                    .thumbnail(0.1f)
                    .into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        }
        holder.name.setText(data.getName());
        holder.delete.setOnClickListener(v -> {
            if (onDeleteListener != null) {
                onDeleteListener.onDelete(holder.getAdapterPosition(), data);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView name;
        private final View delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    /**
     * 删除监听器
     */
    public interface OnDeleteListener {
        /**
         * 当点击删除按钮的时候就会调用这个函数
         *
         * @param position 位置
         * @param file     具体文件
         */
        void onDelete(int position, File file);
    }
}