package com.lfq.mobileoffice.ui.room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseDetailsActivity;
import com.lfq.mobileoffice.data.result.Room;
import com.lfq.mobileoffice.data.result.RoomApplyPager;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.util.Utils;

import java.util.Calendar;

@LoggerName("会议室申请")
@BaseDetailsActivity.RelativePath("room")
@BaseDetailsActivity.ViewResource(R.layout.activity_room_apply)
public class RoomApplyActivity extends BaseDetailsActivity<Room> {

    private EditText startDate;
    private EditText startTime;
    private EditText endDate;
    private EditText endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDate = get(R.id.start_date);
        startTime = get(R.id.start_time);
        endDate = get(R.id.end_date);
        endTime = get(R.id.end_time);

        click(startDate, v -> selectDate((EditText) v));
        click(startTime, v -> selectTime((EditText) v));
        click(endDate, v -> selectDate((EditText) v));
        click(endTime, v -> selectTime((EditText) v));
        click(R.id.post, v -> post());

        RoomApplyListAdapter adapter = new RoomApplyListAdapter(this);

        // 下拉刷新申请记录列表
        SwipeRefreshLayout.OnRefreshListener listener = () -> {
            Api.roomApplyPage(null, getTargetId()).end(() -> swipe(false))
                    .success((Net.OnSuccess<RoomApplyPager>) roomApplyPager -> {
                        boolean empty = roomApplyPager.getData().isEmpty();
                        if (!empty) {
                            adapter.setData(roomApplyPager.getData());
                            adapter.notifyDataSetChanged();
                            map("pager", roomApplyPager.getPager());
                        }
                        get(R.id.recycler).setVisibility(empty ? View.GONE : View.VISIBLE);
                        get(R.id.empty).setVisibility(empty ? View.VISIBLE : View.GONE);
                    }).run();
        };
        swipe(listener);
        map("swipeListener", listener);

        // 上拉加载更多申请记录
        adapter.setOnLoadMoreListener(() -> {
            Api.roomApplyPage(map("pager"), getTargetId())
                    .success((Net.OnSuccess<RoomApplyPager>) roomApplyPager -> {
                        adapter.getData().addAll(roomApplyPager.getData());
                        adapter.notifyDataSetChanged();
                        map("pager", roomApplyPager.getPager());
                    }).run();
        });

        // 初始化申请记录列表
        listener.onRefresh();
    }

    @Override
    protected void onLoad(Room room) {
    }

    /**
     * 给日期编辑框设置点击事件，并且把{@link DatePicker}组件绑定到日期编辑框的tag中
     */
    @SuppressLint("DefaultLocale")
    private void selectDate(EditText text) {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            text.setText(String.format("%d/%d/%d", year, month + 1, dayOfMonth));
            text.setTag(view);
            setDuration();
        });
        dialog.show();
    }

    /**
     * 给事件编辑框设置点击事件，并且把{@link TimePicker}组件绑定到事件编辑框的tag中
     */
    @SuppressLint("DefaultLocale")
    private void selectTime(EditText text) {
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            text.setText(String.format("%d:%d", hourOfDay, minute));
            text.setTag(view);
            setDuration();
        }, 0, 0, true).show();
    }

    /**
     * 提交会议室使用申请
     */
    private void post() {
        try {
            long start = getTimeInMillis(startDate, startTime);
            long end = getTimeInMillis(endDate, endTime);
            String des = Utils.string(get(R.id.des));
            Api.postRoomApply(getTargetId(), start, end, des).success(o -> {
                get(R.id.post).setEnabled(false);
                text(R.id.post, "预约成功,请等待管理员审核.");
                ((SwipeRefreshLayout.OnRefreshListener) map("swipeListener")).onRefresh();
            }).run();
        } catch (NullPointerException e) {
            toast("请选择时间与日期");
        }
    }

    /**
     * 把日期与时间编辑框中的时间以long类型的方式提取出来
     *
     * @param date 日期编辑框
     * @param time 时间编辑框
     * @return {@link Calendar#getTimeInMillis()}
     */
    private long getTimeInMillis(EditText date, EditText time) {
        DatePicker datePicker = (DatePicker) date.getTag();
        TimePicker timePicker = (TimePicker) time.getTag();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getHour(),
                timePicker.getMinute()
        );
        // 60*1000毫秒=1分钟
        int minute = 60 * 1000;
        // 最小单位只取分钟，秒和毫秒都舍弃
        return calendar.getTimeInMillis() / minute * minute;
    }

    /**
     * 显示时长
     */
    @SuppressLint("DefaultLocale")
    private void setDuration() {
        try {
            long start = getTimeInMillis(startDate, startTime);
            long end = getTimeInMillis(endDate, endTime);
            long minute = (end - start) / 1000 / 60;
            if (minute >= 60) {
                text(R.id.duration, String.format("%d小时%d分钟", minute / 60, minute % 60));
            } else {
                text(R.id.duration, minute + "分钟");
            }
        } catch (NullPointerException ignored) {
            text(R.id.duration, "");
        }
    }
}
