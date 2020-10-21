package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 会议室预约申请记录分页
 *
 * @author 李凤强
 */
public class RoomApplyPager {

    private Pager pager;
    private List<RoomApply> data;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<RoomApply> getData() {
        return data;
    }

    public void setData(List<RoomApply> data) {
        this.data = data;
    }
}
