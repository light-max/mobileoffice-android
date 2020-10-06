package com.lfq.mobileoffice.ui.home.message.leave;

import com.lfq.mobileoffice.data.result.LeavePager;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;

/**
 * 消息中所有请假申请
 */
public class MeLeaveAll extends MessageRecyclerFragment<AdapterMeLeave> {
    @Override
    public void onRefresh() {
        Api.leavePage(null).success((Net.OnSuccess<LeavePager>) leavePager -> {
            setData(leavePager.getData());
            map("pager", leavePager.getPager());
        }).run();
    }

    @Override
    public void onLoadMore() {
        Api.leavePage(map("pager")).success((Net.OnSuccess<LeavePager>) leavePager -> {
            addData(leavePager.getData());
            map("pager", leavePager.getPager());
        }).run();
    }
}
