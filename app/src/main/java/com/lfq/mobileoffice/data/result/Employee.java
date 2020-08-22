package com.lfq.mobileoffice.data.result;

/**
 * 员工
 */
public class Employee {
    /**
     * id : 1
     * name : 李凤强1
     * idNumber : 431127200001126098
     * sex : male
     * address : 居住地1
     * contact : 1
     * department : 2
     * createTime : 1594795662755
     * pwd : 1
     */

    private int id;
    private String name;
    private String idNumber;
    private String sex;
    private String address;
    private String contact;
    private int department;
    private long createTime;
    private String pwd;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", department=" + department +
                ", createTime=" + createTime +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
