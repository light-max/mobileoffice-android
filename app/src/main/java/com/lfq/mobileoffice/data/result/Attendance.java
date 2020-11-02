package com.lfq.mobileoffice.data.result;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 考勤详情
 *
 * @author 李凤强
 */
public class Attendance implements Serializable {
    /**
     * year : 2020
     * month : 10
     * day : 25
     * toWork : null
     * offWork : null
     * before : 08:00
     * after : 17:00
     * status : 0
     */

    private int year;
    private int month;
    private int day;
    private String toWork;
    private String offWork;
    private String before;
    private String after;
    private int status;
    private LocalDate date;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getToWork() {
        return toWork;
    }

    public void setToWork(String toWork) {
        this.toWork = toWork;
    }

    public String getOffWork() {
        return offWork;
    }

    public void setOffWork(String offWork) {
        this.offWork = offWork;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    /**
     * 状态
     * <ul>
     *    <li>0.没有考勤信息</li>
     *    <li>1.正常</li>
     *    <li>2.缺勤</li>
     *    <li>3.上班未签到</li>
     *    <li>4.下班未签到</li>
     *    <li>5.迟到</li>
     *    <li>6.早退</li>
     * </ul>
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", toWork=" + toWork +
                ", offWork=" + offWork +
                ", before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", status=" + status +
                '}';
    }

    public LocalDate getDate() {
        if (date == null) {
            date = LocalDate.of(year, month, day);
        }
        return date;
    }
}
