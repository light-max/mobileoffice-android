package com.lfq.mobileoffice.ui.home.room;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Room;

import org.jetbrains.annotations.NotNull;

/**
 * 主页会议室列表适配器
 */
class RoomAdapter extends SimpleRecyclerAdapter<Room, RoomAdapter.ViewHolder> {
    private final int danger;
    private final int safe;

    public RoomAdapter(Base base) {
        super(base);
        danger = base.getContext().getColor(R.color.colorDanger);
        safe = base.getContext().getColor(R.color.colorSafe);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_room;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onBindViewHolder(ViewHolder holder, Room data) {
        holder.name.setText(data.getName());
        holder.capacity.setText(String.format("容量: %d人", data.getCapacity()));
        if (data.getCurrentApplyId() > 0) {
            holder.status.setText("占用");
            holder.status.setTextColor(danger);
        } else {
            holder.status.setText("空闲");
            holder.status.setTextColor(safe);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;
        public final TextView capacity;
        private final TextView status;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            capacity = itemView.findViewById(R.id.capacity);
            status = itemView.findViewById(R.id.status);
        }
    }
}
