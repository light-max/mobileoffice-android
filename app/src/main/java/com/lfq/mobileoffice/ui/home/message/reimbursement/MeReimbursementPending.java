package com.lfq.mobileoffice.ui.home.message.reimbursement;

import com.lfq.mobileoffice.data.result.ReimbursementPager;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;
import com.lfq.mobileoffice.Api;

/**
 * 待审核的报销申请
 */
public class MeReimbursementPending extends MessageRecyclerFragment<AdapterMeReimbursement> {
    @Override
    public void onRefresh() {
        Api.reimbursementPending().success((Net.OnSuccess<ReimbursementPager>) reimbursementPager -> {
            setData(reimbursementPager.getData());
        }).run();
    }
}
