package com.lfq.mobileoffice.scan.data.result;

/**
 * 管理员实体类
 *
 * @author 李凤强
 */
public class Admin {
    /**
     * id : 17
     * username : admin
     * pwd : 1234
     * des :
     * createTime : 1593747656494
     * enable : true
     * enableToInt : 1
     */

    private int id;
    private String username;
    private String pwd;
    private String des;
    private long createTime;
    private boolean enable;
    private int enableToInt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getEnableToInt() {
        return enableToInt;
    }

    public void setEnableToInt(int enableToInt) {
        this.enableToInt = enableToInt;
    }
}
