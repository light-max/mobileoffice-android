package com.lfq.mobileoffice.ui.home.message.leave;

import com.lfq.mobileoffice.data.result.LeavePager;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;

/**
 * 消息中待审核的请假申请
 */
public class MeLeavePending extends MessageRecyclerFragment<AdapterMeLeave> {
    @Override
    public void onRefresh() {
        Api.leavePending().success((Net.OnSuccess<LeavePager>) leavePager -> {
            setData(leavePager.getData());
        }).run();
    }
}
