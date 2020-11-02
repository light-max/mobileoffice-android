package com.lfq.mobileoffice.ui.attendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.result.Attendance;

/**
 * 查看考勤详情
 *
 * @author 李凤强
 */
public class AttendanceDetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_attendance_details);
        Attendance a = (Attendance) getIntent().getSerializableExtra("attendance");
        if (a == null) {
            get(R.id.empty).setVisibility(View.VISIBLE);
        } else {
            text(R.id.status, AttendanceActivity.status[a.getStatus()]);
            if (a.getBefore() != null && a.getAfter() != null) {
                text(R.id.attendance, String.format("%s - %s", a.getBefore(), a.getAfter()));
            }
            text(R.id.to_work, a.getToWork());
            text(R.id.off_work, a.getOffWork());
        }
    }

    /**
     * 启动方法
     *
     * @param context    上下文
     * @param attendance 考勤信息
     */
    public static void start(Context context, Attendance attendance) {
        Intent intent = new Intent(context, AttendanceDetailsActivity.class);
        intent.putExtra("attendance", attendance);
        context.startActivity(intent);
    }
}
