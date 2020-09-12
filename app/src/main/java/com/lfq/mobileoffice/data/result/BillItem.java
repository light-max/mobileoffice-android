package com.lfq.mobileoffice.data.result;

import android.annotation.SuppressLint;

/**
 * 账单项目
 */
public class BillItem {
    /**
     * id : 1
     * reimbursementId : 3
     * name : 项目1
     * amount : 100.0
     */

    private int id;
    private int reimbursementId;
    private String name;
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @SuppressLint("DefaultLocale")
    public String amountString() {
        return String.format("%.2f", amount);
    }
}
