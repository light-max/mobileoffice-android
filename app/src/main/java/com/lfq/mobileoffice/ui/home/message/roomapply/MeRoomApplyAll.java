package com.lfq.mobileoffice.ui.home.message.roomapply;

import com.lfq.mobileoffice.data.result.RoomApplyPager;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;
import com.lfq.mobileoffice.Api;

/**
 * 消息中: 所有预约申请
 */
public class MeRoomApplyAll extends MessageRecyclerFragment<AdapterMeRoomApply> {

    @Override
    public void onRefresh() {
        Api.roomApplyPage(null).success((Net.OnSuccess<RoomApplyPager>) roomApplyPager -> {
            setData(roomApplyPager.getData());
            map("pager", roomApplyPager.getPager());
        }).run();
    }

    @Override
    public void onLoadMore() {
        Api.roomApplyPage(map("pager")).success((Net.OnSuccess<RoomApplyPager>) roomApplyPager -> {
            addData(roomApplyPager.getData());
            map("pager");
        }).run();
    }
}
