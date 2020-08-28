package com.lfq.mobileoffice.data.result;

/**
 * 会议室设备
 */
public class Equipment {

    /**
     * id : 2
     * roomId : 5
     * name : 投影仪
     * des :
     */

    private int id;
    private int roomId;
    private String name;
    private String des;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
