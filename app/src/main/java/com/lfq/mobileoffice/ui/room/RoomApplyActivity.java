package com.lfq.mobileoffice.ui.room;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseDetailsActivity;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.logger.LoggerName;

@LoggerName("会议室申请")
@BaseDetailsActivity.RelativePath("room")
@BaseDetailsActivity.ViewResource(R.layout.activity_room_apply)
public class RoomApplyActivity extends BaseDetailsActivity<Room> {
    @Override
    protected void onLoad(Room room) {

    }
}
