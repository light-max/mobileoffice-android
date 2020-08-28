package com.lfq.mobileoffice.net;

import android.os.Handler;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.data.LoginEmployeeData;
import com.lfq.mobileoffice.data.result.Employee;
import com.lfq.mobileoffice.data.result.Equipment;
import com.lfq.mobileoffice.data.result.Notice;
import com.lfq.mobileoffice.data.result.NoticePager;
import com.lfq.mobileoffice.data.result.Pager;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.data.result.RoomApplyPager;
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
    public static Net.Builder roomsPage(@Nullable Pager currentPager) {
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
    public static Net.Builder noticesPage(@Nullable Pager currentPager) {
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

    /**
     * 请求这个会议室下所有的设备
     *
     * @param roomId 会议室id
     */
    public static Net.Builder equipments(int roomId) {
        return Net.builder().method(Net.GET)
                .typeListOf(Equipment.class)
                .handler(new Handler())
                .url("/equipment/list")
                .param("roomId", roomId);
    }

    /**
     * 提交会议室使用申请
     *
     * @param roomId 会议室id
     * @param start  开始时间
     * @param end    结束时间
     * @param des    附加消息
     */
    public static Net.Builder postRoomApply(int roomId, long start, long end, String des) {
        Net.Data data = new Net.Data()
                .addParam("roomId", roomId)
                .addParam("start", start)
                .addParam("end", end)
                .addParam("des", des);
        return Net.builder().method(Net.POST)
                .handler(new Handler())
                .url("/apply/room/post")
                .data(data);
    }

    /**
     * 分页获取某个会议室申请记录列表
     *
     * @param pager  当前分页
     * @param roomId 会议室id
     */
    public static Net.Builder roomApplyPage(@Nullable Pager pager, int roomId) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(RoomApplyPager.class)
                .handler(new Handler())
                .url("/apply/room/toroom/list/{currentPage}")
                .param("roomId", roomId);
        if (pager != null) {
            builder.path("currentPage", pager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 分页获取"我的"的会议室申请记录列表
     *
     * @param pager 当前分页
     */
    public static Net.Builder roomApplyPage(@Nullable Pager pager) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(RoomApplyPager.class)
                .handler(new Handler())
                .url("/apply/room/list/{currentPage}");
        if (pager != null) {
            builder.path("currentPage", pager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 分页获取"我的"待受理的会议室申请记录列表<br>
     * 一次性加载完所有申请记录, 不需要分页
     */
    public static Net.Builder roomApplyPending() {
        return Net.builder().method(Net.GET)
                .typeOf(RoomApplyPager.class)
                .handler(new Handler())
                .url("/apply/room/list/")
                .param("status", 1);
    }
}
