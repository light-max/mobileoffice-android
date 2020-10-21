package com.lfq.mobileoffice.ui.room;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Equipment;

/**
 * 会议室列表适配器
 *
 * @author 李凤强
 */
class EquipmentAdapter extends SimpleRecyclerAdapter<Equipment, EquipmentAdapter.ViewHolder> {

    public EquipmentAdapter(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_equipment;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Equipment data) {
        holder.name.setText(data.getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
