package com.lfq.mobileoffice.data.result;

/**
 * 资源文件
 *
 * @author 李凤强
 */
public class Resource {

    /**
     * id : 4971160308c4850195cd5452fb12db05
     * type : image/png
     * name : 屏幕截图(1113).png
     * size : 2441847
     * employeeId : 1
     */

    private String id;
    private String type;
    private String name;
    private long size;
    private int employeeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", employeeId=" + employeeId +
                '}';
    }
}
