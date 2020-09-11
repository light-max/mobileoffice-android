package com.lfq.mobileoffice.data.request;

import java.util.List;

/**
 * 添加报销单请求数据
 */
public class ReimbursementPostData {

    /**
     * payee : fdsaf
     * card : cardssfsd
     * des : dessfsda
     * resources : ["20","23423","fdasf"]
     * bills : [{"name":"账单1","amount":1333.44},{"name":"账单2","amount":1534.44}]
     */

    private String payee;
    private String card;
    private String des;
    private List<String> resources;
    private List<BillItem> bills;

    public ReimbursementPostData(String payee, String card, String des, List<String> resources, List<BillItem> bills) {
        this.payee = payee;
        this.card = card;
        this.des = des;
        this.resources = resources;
        this.bills = bills;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public List<BillItem> getBills() {
        return bills;
    }

    public void setBills(List<BillItem> bills) {
        this.bills = bills;
    }
}
