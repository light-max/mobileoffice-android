package com.lfq.mobileoffice.data.result;

import org.jetbrains.annotations.NotNull;

/**
 * 会议室
 */
public class Room {
    /**
     * id : 1
     * name : 会议室1
     * location :
     * capacity : 1
     * currentApplyId : 0
     */

    private int id;
    private String name;
    private String location;
    private int capacity;
    private long currentApplyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getCurrentApplyId() {
        return currentApplyId;
    }

    public void setCurrentApplyId(long currentApplyId) {
        this.currentApplyId = currentApplyId;
    }

    @NotNull
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", currentApplyId=" + currentApplyId +
                '}';
    }
}