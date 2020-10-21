package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 会议室分页数据
 *
 * @author 李凤强
 */
public class RoomPager {

    /**
     * data : [{"id":1,"name":"会议室1","location":"","capacity":1},{"id":2,"name":"会议室0","location":"地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0地址0","capacity":120},{"id":3,"name":"会议室11","location":"地址1地址1地址1地址1地址1地址11","capacity":346},{"id":4,"name":"会议室2-","location":"地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2地址2-","capacity":251},{"id":5,"name":"会议室3","location":"地址3地址3地址3","capacity":344},{"id":6,"name":"会议室4","location":"地址4地址4地址4","capacity":285},{"id":7,"name":"会议室5","location":"地址5","capacity":72},{"id":8,"name":"会议室6","location":"地址6地址6地址6地址6地址6地址6地址6地址6地址6地址6地址6地址6地址6地址6地址6","capacity":145},{"id":10,"name":"会议室8","location":"地址8地址8地址8地址8地址8地址8地址8地址8地址8地址8","capacity":299},{"id":11,"name":"会议室9","location":"地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9地址9","capacity":375},{"id":12,"name":"会议室10","location":"地址10地址10","capacity":37},{"id":13,"name":"会议室11","location":"地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11地址11","capacity":119},{"id":14,"name":"会议室12","location":"地址12地址12地址12","capacity":236},{"id":15,"name":"会议室13","location":"地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13地址13","capacity":140},{"id":16,"name":"会议室14","location":"地址14","capacity":288}]
     * pager : {"pageCount":7,"currentPage":1,"url":"/room/list/","size":15,"tailAppend":""}
     */

    private Pager pager;
    private List<Room> data;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Room> getData() {
        return data;
    }

    public void setData(List<Room> data) {
        this.data = data;
    }
}
