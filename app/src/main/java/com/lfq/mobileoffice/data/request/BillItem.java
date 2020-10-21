package com.lfq.mobileoffice.data.request;

/**
 * 账单项目类，发送网络请求用
 *
 * @author 李凤强
 */
public class BillItem {
    private String name;
    private Float amount;

    public BillItem(String name, Float amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
