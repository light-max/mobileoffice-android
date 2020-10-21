package com.lfq.mobileoffice.ui.home.message.roomapply;

import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.data.result.RoomApply;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageDetails;
import com.lfq.mobileoffice.ui.room.RoomDetailsActivity;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 会议室预约申请记录适配器
 *
 * @author 李凤强
 */
public class AdapterMeRoomApply extends SimpleRecyclerAdapter<RoomApply, AdapterMeRoomApply.ViewHolder>
        implements SimpleRecyclerAdapter.OnItemClickListener<RoomApply> {
    private final Map<Integer, Pair<Integer, String>> status;
    private Base base;

    public AdapterMeRoomApply(Base base) {
        super(base);
        this.base = base;
        status = new HashMap<>();
        status.put(1, new Pair<>(base.getContext().getColor(R.color.colorAccent), "待受理"));
        status.put(2, new Pair<>(base.getContext().getColor(R.color.colorSafe), "已同意"));
        status.put(3, new Pair<>(base.getContext().getColor(R.color.colorDanger), "被拒绝"));
        status.put(4, new Pair<>(base.getContext().getColor(android.R.color.darker_gray), "已过期"));
        status.put(5, new Pair<>(base.getContext().getColor(android.R.color.black), "已使用"));
        setOnItemClickListener(this);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_message_room_apply;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, RoomApply data) {
        String dateFormat = "YYYY/MM/dd HH:mm";
        holder.start.setText(Utils.dateFormat(dateFormat, data.getStart()));
        holder.end.setText(Utils.dateFormat(dateFormat, data.getEnd()));
        Pair<Integer, String> status = this.status.get(data.getStatus());
        if (status != null) {
            holder.status.setTextColor(status.first);
            holder.status.setText(status.second);
        }
        // 网络请求，获取会议室名称
        Api.room(data.getRoomId()).toast(false)
                .success((Net.OnSuccess<Room>) room -> {
                    holder.roomName.setText(room.getName());
                }).run();
        // 点击会议室名打开会议室详情
        holder.roomName.setOnClickListener(v -> {
            new RoomDetailsActivity().start(base.getContext(), data.getRoomId());
        });
    }

    /**
     * 点击显示详情
     *
     * @param data     数据
     * @param position 下标
     */
    @Override
    public void onItemClick(RoomApply data, int position) {
        MessageDetails.roomApply(status, base.getContext(), data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView start;
        private final TextView end;
        private final TextView status;
        private final TextView roomName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            status = itemView.findViewById(R.id.status);
            roomName = itemView.findViewById(R.id.roomName);
        }
    }
}
