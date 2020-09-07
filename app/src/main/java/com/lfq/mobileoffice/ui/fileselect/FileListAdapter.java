package com.lfq.mobileoffice.ui.fileselect;

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

    public FileListAdapter(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_file;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, File data) {
        if (data.isDirectory()) {
            holder.icon.setImageResource(R.drawable.ic_baseline_folder_24);
        } else {
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
        }
        holder.name.setText(data.getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}
