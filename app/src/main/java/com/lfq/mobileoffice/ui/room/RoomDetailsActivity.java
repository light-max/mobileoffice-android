package com.lfq.mobileoffice.ui.room;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseDetailsActivity;
import com.lfq.mobileoffice.data.result.Equipment;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.net.Net;

import java.util.List;

/**
 * 会议室详情
 *
 * @author 李凤强
 */
@LoggerName("会议室详情")
@BaseDetailsActivity.ViewResource(R.layout.activity_room_details)
@BaseDetailsActivity.RelativePath("room")
public class RoomDetailsActivity extends BaseDetailsActivity<Room> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 预约按钮
        click(R.id.apply, (view) ->
                new RoomApplyActivity().start(this, getTargetId())
        );
        // 设备列表
        EquipmentAdapter adapter = new EquipmentAdapter(this);
        // 获取设备
        Api.equipments(getTargetId()).success((Net.OnSuccess<List<Equipment>>) equipment -> {
            if (!equipment.isEmpty()) {
                adapter.setData(equipment);
                adapter.notifyDataSetChanged();
            }
            get(R.id.recycler).setVisibility(equipment.isEmpty() ? View.GONE : View.VISIBLE);
            get(R.id.empty).setVisibility(equipment.isEmpty() ? View.VISIBLE : View.GONE);
        }).run();
        // 点击查看
        adapter.setOnItemClickListener((data, position) -> {
            new AlertDialog.Builder(this)
                    .setTitle(data.getName())
                    .setMessage(data.getDes())
                    .setNegativeButton("取消", null)
                    .show();
        });
    }

    @Override
    protected void onLoad(Room room) {
        text(R.id.name).setText(room.getName());
        text(R.id.location).setText(room.getLocation());
        text(R.id.capacity).setText(String.valueOf(room.getCapacity()));
        if (room.getCurrentApplyId() > 0) {
            text(R.id.status, "占用");
        } else {
            text(R.id.status, "空闲");
        }
    }
}
