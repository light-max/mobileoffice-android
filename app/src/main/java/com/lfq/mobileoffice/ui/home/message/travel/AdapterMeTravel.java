package com.lfq.mobileoffice.ui.home.message.travel;

import android.annotation.SuppressLint;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Travel;
import com.lfq.mobileoffice.ui.home.message.MessageDetails;
import com.lfq.mobileoffice.ui.home.message.StatusAdapter;
import com.lfq.mobileoffice.util.Utils;

/**
 * 出差申请记录适配器
 */
public class AdapterMeTravel extends StatusAdapter<Travel, AdapterMeTravel.ViewHolder> {

    public AdapterMeTravel(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_travel;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(ViewHolder holder, Travel data) {
        holder.start.setText(Utils.dateFormat(dateFormat, data.getStart()));
        holder.end.setText(Utils.dateFormat(dateFormat, data.getEnd()));
        holder.des.setText(data.getDes());
        holder.address.setText("地址：" + data.getAddress());
        Pair<Integer, String> status = this.status.get(data.getStatus());
        if (status != null) {
            holder.status.setText(status.second);
            holder.status.setTextColor(status.first);
        }
    }

    @Override
    public void onItemClick(Travel data, int position) {
        MessageDetails.travel(status, base.getContext(), data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView address;
        private final TextView des;
        private final TextView status;
        private final TextView start;
        private final TextView end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            des = itemView.findViewById(R.id.des);
            status = itemView.findViewById(R.id.status);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
        }
    }
}
