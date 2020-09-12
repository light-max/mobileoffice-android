package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 出差
 */
public class Travel {
    /**
     * id : 5
     * employeeId : 1
     * des : 发撒旦范德萨
     * address : 发生打发
     * status : 2
     * start : 1599696000000
     * end : 1599757200000
     * createTime : 1599703178550
     * resources : [{"id":"27c08d616b2b17942ffc8581e2f59ffc","type":"application/octet-stream","name":".nomedia","size":0,"employeeId":1},{"id":"97fb81e433e8950ba852d9de35b50f0a","type":"image/jpeg","name":"IMG_20200905_054714.jpg","size":200364,"employeeId":1}]
     */

    private int id;
    private int employeeId;
    private String des;
    private String address;
    private int status;
    private long start;
    private long end;
    private long createTime;
    private List<ResourcesBean> resources;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<ResourcesBean> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesBean> resources) {
        this.resources = resources;
    }

    public static class ResourcesBean {
        /**
         * id : 27c08d616b2b17942ffc8581e2f59ffc
         * type : application/octet-stream
         * name : .nomedia
         * size : 0
         * employeeId : 1
         */

        private String id;
        private String type;
        private String name;
        private int size;
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }
    }
}
