package com.lfq.mobileoffice.ui.room;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.LoginEmployeeData;
import com.lfq.mobileoffice.data.result.Employee;
import com.lfq.mobileoffice.data.result.RoomApply;
import com.lfq.mobileoffice.util.Utils;

class RoomApplyListAdapter extends SimpleRecyclerAdapter<RoomApply, RoomApplyListAdapter.ViewHolder> {
    private final int[] colors;
    private final Employee me;

    public RoomApplyListAdapter(Base base) {
        super(base);
        colors = new int[]{
                base.getContext().getColor(R.color.colorAccent),
                base.getContext().getColor(R.color.colorSafe),
                base.getContext().getColor(R.color.colorDanger),
                base.getContext().getColor(android.R.color.darker_gray),
                base.getContext().getColor(android.R.color.black)
        };
        me = LoginEmployeeData.getInstance().getValue();
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_room_apply;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, RoomApply data) {
        String dateFormat = "YYYY/MM/dd HH:mm";
        holder.start.setText(Utils.dateFormat(dateFormat, data.getStart()));
        holder.end.setText(Utils.dateFormat(dateFormat, data.getEnd()));
        switch (data.getStatus()) {
            case 1:
                holder.status.setText("待受理");
                holder.status.setTextColor(colors[0]);
                break;
            case 2:
                holder.status.setText("已同意");
                holder.status.setTextColor(colors[1]);
                break;
            case 3:
                holder.status.setText("被拒绝");
                holder.status.setTextColor(colors[2]);
                break;
            case 4:
                holder.status.setText("已过期");
                holder.status.setTextColor(colors[3]);
                break;
            case 5:
                holder.status.setText("已使用");
                holder.status.setTextColor(colors[4]);
        }
        holder.me.setVisibility(me.getId() == data.getEmployeeId() ? View.VISIBLE : View.GONE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView start;
        private final TextView end;
        private final TextView status;
        private final TextView me;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            status = itemView.findViewById(R.id.status);
            me = itemView.findViewById(R.id.me);
        }
    }
}
