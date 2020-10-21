package com.lfq.mobileoffice.data.result;

import android.annotation.SuppressLint;

import java.util.List;

/**
 * 报销表
 *
 * @author 李凤强
 */
public class Reimbursement {

    /**
     * id : 3
     * employeeId : 1
     * des : 详情描述
     * payeeName : 里风强
     * bankCard : 1231203103040
     * status : 2
     * createTime : 1599786005629
     * resources : [{"id":"da7ad238fad7b8ef4c55736b41db75d5","type":"application/octet-stream","name":".nomedia","size":0,"employeeId":1},{"id":"e4e2a99dd93d4350a492a502cba8af90","type":"image/jpeg","name":"IMG_20200905_054714.jpg","size":200364,"employeeId":1}]
     * billItems : [{"id":1,"reimbursementId":3,"name":"项目1","amount":100},{"id":2,"reimbursementId":3,"name":"项目2","amount":200},{"id":3,"reimbursementId":3,"name":"项目3","amount":300}]
     * total : 600.0
     */

    private int id;
    private int employeeId;
    private String des;
    private String payeeName;
    private String bankCard;
    private int status;
    private long createTime;
    private List<Resource> resources;
    private List<BillItem> billItems;
    private double total;

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

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    @SuppressLint("DefaultLocale")
    public String totalString() {
        return String.format("%.2f", total);
    }
}
