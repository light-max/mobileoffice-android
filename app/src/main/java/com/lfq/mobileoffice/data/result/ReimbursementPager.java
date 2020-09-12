package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 报销申请分页
 */
public class ReimbursementPager {

    private Pager pager;
    private List<Reimbursement> data;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Reimbursement> getData() {
        return data;
    }

    public void setData(List<Reimbursement> data) {
        this.data = data;
    }
}
