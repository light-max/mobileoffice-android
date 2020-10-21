package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 请假记录分页对象
 *
 * @author 李凤强
 */
public class LeavePager {

    private Pager pager;
    private List<Leave> data;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Leave> getData() {
        return data;
    }

    public void setData(List<Leave> data) {
        this.data = data;
    }
}
