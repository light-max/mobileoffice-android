package com.lfq.mobileoffice.ui.attendance;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;

/**
 * 考勤activity
 */
public class AttendanceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_attendance);
    }
}
