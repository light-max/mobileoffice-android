package com.lfq.mobileoffice.scan.data.result;

public class SignInSuccess {

    /**
     * id : 12
     * employeeId : 266
     * signInTimeId : 5
     * type : 2
     * createTime : 1601560262890
     * departmentName : 艾欧尼亚
     * employeeName : 宋江
     * signInDateTime : 2020.10.01 21:51:02
     * signInType : 下班
     */

    private int id;
    private int employeeId;
    private int signInTimeId;
    private int type;
    private long createTime;
    private String departmentName;
    private String employeeName;
    private String signInDateTime;
    private String signInType;

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

    public int getSignInTimeId() {
        return signInTimeId;
    }

    public void setSignInTimeId(int signInTimeId) {
        this.signInTimeId = signInTimeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSignInDateTime() {
        return signInDateTime;
    }

    public void setSignInDateTime(String signInDateTime) {
        this.signInDateTime = signInDateTime;
    }

    public String getSignInType() {
        return signInType;
    }

    public void setSignInType(String signInType) {
        this.signInType = signInType;
    }

    @Override
    public String toString() {
        return "SignInSuccess{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", signInTimeId=" + signInTimeId +
                ", type=" + type +
                ", createTime=" + createTime +
                ", departmentName='" + departmentName + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", signInDateTime='" + signInDateTime + '\'' +
                ", signInType='" + signInType + '\'' +
                '}';
    }
}
