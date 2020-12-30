package com.lfq.mobileoffice.ui.attendance;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lfq.calendar.AttendanceCalendarView;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.result.Attendance;
import com.lfq.mobileoffice.net.Net;

import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;

/**
 * 考勤activity
 *
 * @author 李凤强
 */
public class AttendanceActivity extends BaseActivity implements
        AttendanceCalendarView.OnDrawStatusListener,
        AttendanceCalendarView.OnMonthSelectedListener,
        AttendanceCalendarView.OnDateSelectedListener {

    private AttendanceCalendarView view;
    private int[] colors;
    public static String[] status = new String[]{
            "",
            "正常",
            "缺勤",
            "上班未签到",
            "下班未签到",
            "迟到",
            "早退"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_attendance);
        view = get(R.id.calendar);
        view.setOnDrawStatusListener(this);
        view.setOnMonthSelectedListener(this);
        view.setOnDateSelectedListener(this);
        initStatusColor();
        LocalDate currentDate = LocalDate.now(TimeZone.getTimeZone("GMT+8:00").toZoneId());
        onMonthSelected(currentDate);
    }

    /**
     * @see Attendance#getStatus()
     */
    private void initStatusColor() {
        colors = new int[]{
                getColor(android.R.color.darker_gray),
                getColor(R.color.colorSafe),
                getColor(R.color.colorDanger),
                getColor(R.color.colorWarning),
                getColor(R.color.colorWarning),
                getColor(R.color.colorWarning),
                getColor(R.color.colorWarning),
        };
    }

    @Override
    public void onDrawStatus(TextView view, LocalDate date) {
        Attendance a = map(date);
        if (a != null) {
            int color = colors[a.getStatus()];
            String s = status[a.getStatus()];
            view.setTextColor(color);
            view.setText(s);
        }
    }

    @Override
    public void onMonthSelected(LocalDate month) {
        Net.OnSuccess<List<Attendance>> success = attendance -> {
            for (Attendance a : attendance) {
                map(a.getDate(), a);
            }
//            view.post(() -> view.updateUI(month));
            view.post(() -> {
                view.updateUI();
                view.invalidate();
            });
//            view.postDelayed(() -> view.updateUI(month), 5000);
        };
        Api.attendance(month.getYear(), month.getMonthValue())
                .success(success)
                .run();
        LocalDate previous = month.minusMonths(1);
        Api.attendance(previous.getYear(), previous.getMonthValue())
                .success(success)
                .run();
        LocalDate next = month.plusMonths(1);
        Api.attendance(next.getYear(), next.getMonthValue())
                .success(success)
                .run();
    }

    @Override
    public void onDateSelected(LocalDate date) {
        AttendanceDetailsActivity.start(this, map(date));
    }
}
