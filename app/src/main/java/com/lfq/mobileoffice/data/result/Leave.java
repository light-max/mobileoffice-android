package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 请假记录
 *
 * @author 李凤强
 */
public class Leave {
    /**
     * id : 9
     * employeeId : 1
     * type : 1
     * des : 方法打算
     * status : 1
     * start : 1599696000000
     * end : 1599724800000
     * createTime : 1599703278716
     * typeName : 事假
     * resources : [{"id":"0ee57d57a4cd13d29e3db44be5025867","type":"image/jpeg","name":"IMG_20200905_054714.jpg","size":200364,"employeeId":1}]
     */

    private int id;
    private int employeeId;
    private int type;
    private String des;
    private int status;
    private long start;
    private long end;
    private long createTime;
    private String typeName;
    private List<Resource> resources;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
