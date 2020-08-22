package com.lfq.mobileoffice.data.result;

/**
 * 部门
 */
public class Department {

    /**
     * id : 1
     * name : 部门名称
     * des : 部门描述
     * count : 10
     * createTime : 1595933309376
     */

    private int id;
    private String name;
    private String des;
    private int count;
    private long createTime;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", count=" + count +
                ", createTime=" + createTime +
                '}';
    }
}
