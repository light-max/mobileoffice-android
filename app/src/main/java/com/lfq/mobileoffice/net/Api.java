package com.lfq.mobileoffice.net;

import android.os.Handler;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.data.LoginEmployeeData;
import com.lfq.mobileoffice.data.request.BillItem;
import com.lfq.mobileoffice.data.request.ReimbursementPostData;
import com.lfq.mobileoffice.data.result.AFLType;
import com.lfq.mobileoffice.data.result.Employee;
import com.lfq.mobileoffice.data.result.Equipment;
import com.lfq.mobileoffice.data.result.LeavePager;
import com.lfq.mobileoffice.data.result.Notice;
import com.lfq.mobileoffice.data.result.NoticePager;
import com.lfq.mobileoffice.data.result.Pager;
import com.lfq.mobileoffice.data.result.ReimbursementPager;
import com.lfq.mobileoffice.data.result.Resource;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.data.result.RoomApplyPager;
import com.lfq.mobileoffice.data.result.RoomPager;
import com.lfq.mobileoffice.data.result.TravelPager;

import java.io.File;
import java.util.List;

/**
 * 网络请求接口工具类
 */
public class Api {

    /**
     * 把url地址加上端口
     *
     * @param path
     * @return
     */
    public static String url(String path) {
        return new File(Net.baseUrl, path).getPath();
    }

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

    /**
     * 查询请假类型
     */
    public static Net.Builder aflTypes() {
        return Net.builder().method(Net.GET)
                .handler(new Handler())
                .typeListOf(AFLType.class)
                .url("/leave/application/types");
    }

    /**
     * 文件上传接口
     *
     * @param file 文件
     */
    public static Net.Builder fileUpload(File file) {
        return Net.builder().method(Net.POST)
                .typeOf(Resource.class)
                .handler(new Handler())
                .url("/employee/resource")
                .param("file", file);
    }

    /**
     * 文件删除接口
     */
    public static Net.Builder fileDelete(Resource resource) {
        return Net.builder().method(Net.DELETE)
                .handler(new Handler())
                .url("/employee/resource/{resourceId}")
                .path("resourceId", resource.getId());
    }

    /**
     * 批量删除文件
     */
    public static Net.Builder filesDelete(List<String> resourceIds) {
        return Net.builder().method(Net.DELETE)
                .handler(new Handler())
                .url("/employee/resource")
                .param("ids", resourceIds);
    }

    /**
     * 发送请假请求
     */
    public static Net.Builder postLeaveApplication(
            int type, String des, long start, long end, List<String> resource
    ) {
        return Net.builder().method(Net.POST)
                .handler(new Handler())
                .url("/leave/application/post")
                .param("type", type)
                .param("des", des)
                .param("start", start)
                .param("end", end)
                .param("resource", resource);
    }

    /**
     * 发送出差请求
     */
    public static Net.Builder postTravelApplication(
            String des, String address, long start, long end, List<String> resource
    ) {
        return Net.builder().method(Net.POST)
                .handler(new Handler())
                .url("/travel/application/post")
                .param("des", des)
                .param("address", address)
                .param("start", start)
                .param("end", end)
                .param("resource", resource);
    }

    /**
     * 检查{@link BillItem}数据是否符合要求
     */
    public static Net.Builder checkBillItem(BillItem billItem) {
        return Net.builder().method(Net.POST)
                .handler(new Handler())
                .url("/reimbursement/application/billitem/check")
                .json(billItem);
    }

    /**
     * 提交一个报销请求
     *
     * @param data 请求数据组合对象
     */
    public static Net.Builder postReimbursementApplication(ReimbursementPostData data) {
        return Net.builder().method(Net.POST)
                .handler(new Handler())
                .url("/reimbursement/application/post")
                .json(data);
    }

    /**
     * 分页查询“我的”请假申请记录
     *
     * @param pager 当前分页数据
     */
    public static Net.Builder leavePage(@Nullable Pager pager) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(LeavePager.class)
                .handler(new Handler())
                .url("/leave/application/list/{currentPage}");
        if (pager != null) {
            builder.path("currentPage", pager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 查询所有“我的”待批准请假申请记录
     */
    public static Net.Builder leavePending() {
        return Net.builder().method(Net.GET)
                .typeOf(LeavePager.class)
                .handler(new Handler())
                .url("/leave/application/list")
                .param("status", 1);
    }

    /**
     * 分页查询“我的”出差申请记录
     *
     * @param pager 当前分页数据
     */
    public static Net.Builder travelPage(@Nullable Pager pager) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(TravelPager.class)
                .handler(new Handler())
                .url("/travel/application/list/{currentPage}");
        if (pager != null) {
            builder.path("currentPage", pager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 查询所有“我的”待批准出差申请记录
     */
    public static Net.Builder travelPending() {
        return Net.builder().method(Net.GET)
                .typeOf(TravelPager.class)
                .handler(new Handler())
                .url("/travel/application/list/")
                .param("status", 1);
    }

    /**
     * 分页查询“我的”报销申请
     *
     * @param pager 当前分页状态
     */
    public static Net.Builder reimbursementPage(@Nullable Pager pager) {
        Net.Builder builder = Net.builder().method(Net.GET)
                .typeOf(ReimbursementPager.class)
                .handler(new Handler())
                .url("/reimbursement/application/list/{currentPage}");
        if (pager != null) {
            builder.path("currentPage", pager.getCurrentPage() + 1);
        }
        return builder;
    }

    /**
     * 查询所有“我的”待批准报销申请记录
     */
    public static Net.Builder reimbursementPending() {
        return Net.builder().method(Net.GET)
                .typeOf(ReimbursementPager.class)
                .handler(new Handler())
                .url("/reimbursement/application/list")
                .param("status", 1);
    }
}
