package com.lfq.mobileoffice.ui.room;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseDetailsActivity;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.logger.LoggerName;

/**
 * 会议室详情
 */
@LoggerName("会议室详情")
@BaseDetailsActivity.ViewResource(R.layout.activity_room_details)
@BaseDetailsActivity.RelativePath("room")
public class RoomDetailsActivity extends BaseDetailsActivity<Room> {
    @Override
    protected void onLoad(Room room) {
        text(R.id.name).setText(room.getName());
        text(R.id.location).setText(room.getLocation());
        text(R.id.capacity).setText(String.valueOf(room.getCapacity()));
        click(R.id.apply, (view) -> {
            new RoomApplyActivity().start(this, room.getId());
        });
    }
}
