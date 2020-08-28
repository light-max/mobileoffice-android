package com.lfq.mobileoffice.data.result;

/**
 * 会议室预约申请
 */
public class RoomApply {

    /**
     * id : 8
     * roomId : 1
     * employeeId : 1
     * start : 1598227233486
     * end : 1598227533486
     * status : 1
     * des :
     * createTime : 1598161173971
     */

    private int id;
    private int roomId;
    private int employeeId;
    private long start;
    private long end;
    private int status;
    private String des;
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
