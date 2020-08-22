package com.lfq.mobileoffice.net;

import android.os.Handler;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.data.LoginEmployeeData;
import com.lfq.mobileoffice.data.result.Employee;
import com.lfq.mobileoffice.data.result.Notice;
import com.lfq.mobileoffice.data.result.NoticePager;
import com.lfq.mobileoffice.data.result.Pager;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.data.result.RoomPager;

/**
 * 网络请求接口工具类
 */
public class Api {
    /**
     * 登陆请求
     *
     * @param id  员工工号
     * @param pwd 登陆密码
     */
    public static Net.Builder login(String id, String pwd) {
        return Net.builder().method(Net.POST)
                .typeOf(Employee.class)
                .handler(new Handler())
                .observe(LoginEmployeeData.getInstance())
                .url("employee/login")
                .param("id", id)
                .param("pwd", pwd);
    }

    /**
     * 请求下一页会议室列表
     *
     * @param currentPager 当前分页数据
     */
    public static Net.Builder roomsNextPage(@Nullable Pager currentPager) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(RoomPager.class)
                .handler(new Handler())
                .url("/room/list/{currentPage}");
        if (currentPager != null) {
            builder.path("currentPage", currentPager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 请求单个会议室的信息
     *
     * @param roomId 会议室id
     */
    public static Net.Builder room(int roomId) {
        return Net.builder().method(Net.GET)
                .typeOf(Room.class)
                .handler(new Handler())
                .url("/room/{roomId}")
                .path("roomId", roomId);
    }

    /**
     * 请求下一页通知列表
     *
     * @param currentPager 当前分页数据
     */
    public static Net.Builder noticesNextPage(@Nullable Pager currentPager) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(NoticePager.class)
                .handler(new Handler())
                .url("/notice/list/{currentPage}");
        if (currentPager != null) {
            builder.path("currentPage", currentPager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 请求单个公告
     *
     * @param noticeId 公告id
     */
    public static Net.Builder notice(int noticeId) {
        return Net.builder().method(Net.GET)
                .typeOf(Notice.class)
                .handler(new Handler())
                .url("/notice/{noticeId}")
                .path("noticeId", noticeId);
    }
}
