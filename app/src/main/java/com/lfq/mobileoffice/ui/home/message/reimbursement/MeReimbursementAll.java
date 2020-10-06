package com.lfq.mobileoffice.ui.home.message.reimbursement;

import com.lfq.mobileoffice.data.result.ReimbursementPager;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;
import com.lfq.mobileoffice.Api;

/**
 * 所有报销申请
 */
public class MeReimbursementAll extends MessageRecyclerFragment<AdapterMeReimbursement> {
    @Override
    public void onRefresh() {
        Api.reimbursementPage(null).success((Net.OnSuccess<ReimbursementPager>) reimbursementPager -> {
            setData(reimbursementPager.getData());
            map("pager", reimbursementPager.getPager());
        }).run();
    }

    @Override
    public void onLoadMore() {
        Api.reimbursementPage(map("pager")).success((Net.OnSuccess<ReimbursementPager>) reimbursementPager -> {
            addData(reimbursementPager.getData());
            map("pager", reimbursementPager.getPager());
        }).run();
    }
}
