package com.lfq.mobileoffice.ui.home.message.leave;

import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Leave;
import com.lfq.mobileoffice.ui.home.message.MessageDetails;
import com.lfq.mobileoffice.ui.home.message.StatusAdapter;
import com.lfq.mobileoffice.util.Utils;

/**
 * 请假申请记录适配器
 */
public class AdapterMeLeave extends StatusAdapter<Leave, AdapterMeLeave.ViewHolder> {

    public AdapterMeLeave(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_leave;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Leave data) {
        holder.start.setText(Utils.dateFormat(dateFormat, data.getStart()));
        holder.end.setText(Utils.dateFormat(dateFormat, data.getEnd()));
        holder.typeName.setText(data.getTypeName());
        Pair<Integer, String> status = this.status.get(data.getStatus());
        if (status != null) {
            holder.status.setText(status.second);
            holder.status.setTextColor(status.first);
        }
    }

    @Override
    public void onItemClick(Leave data, int position) {
        MessageDetails.leave(status, base.getContext(), data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView typeName;
        private final TextView status;
        private final TextView start;
        private final TextView end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeName = itemView.findViewById(R.id.typeName);
            status = itemView.findViewById(R.id.status);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
        }
    }
}
