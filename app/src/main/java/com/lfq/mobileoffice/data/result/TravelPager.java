package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 出差分页对象
 *
 * @author 李凤强
 */
public class TravelPager {

    private Pager pager;
    private List<Travel> data;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Travel> getData() {
        return data;
    }

    public void setData(List<Travel> data) {
        this.data = data;
    }
}
