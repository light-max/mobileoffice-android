package com.lfq.mobileoffice.scan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.scan.data.result.SignInSuccess;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * 扫描结果activity
 */
public class ScanningActivity extends BaseActivity {

    private TextView status;
    private TextView error;
    private TextView type;
    private TextView employee;
    private TextView department;
    private TextView date;
    private Button scanning;
    private TextView next;
    private int nextInt = 0;
    private boolean isFront = false;
    private Handler uiHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        uiHandler = new Handler();
        status = get(R.id.status);
        error = get(R.id.error);
        type = get(R.id.type);
        employee = get(R.id.employee);
        department = get(R.id.department);
        date = get(R.id.date);
        scanning = get(R.id.scanning);
        next = get(R.id.next);
        click(R.id.scanning, v -> {
            IntentIntegrator integrator = new IntentIntegrator(ScanningActivity.this);
            integrator.setCameraId(0);
            integrator.initiateScan();
        });
        scanning.callOnClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                signIn(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 签到成功回调接口
     */
    private Net.OnSuccess<SignInSuccess> signInSuccess = s -> {
        status.setText("签到成功");
        status.setTextColor(getColor(R.color.colorSafe));
        error.setVisibility(View.GONE);
        type.setText(s.getSignInType());
        type.setVisibility(View.VISIBLE);
        employee.setText(s.getEmployeeName());
        employee.setVisibility(View.VISIBLE);
        department.setText(s.getDepartmentName());
        department.setVisibility(View.VISIBLE);
        date.setText(s.getSignInDateTime());
        date.setVisibility(View.VISIBLE);
        nextInt = 5;
        continueScanning();
    };

    /**
     * 签到失败回调接口
     */
    private Net.OnFailure signInFailure = message -> {
        status.setText("签到失败");
        status.setTextColor(getColor(R.color.colorDanger));
        error.setVisibility(View.VISIBLE);
        error.setText(message);
        type.setVisibility(View.GONE);
        employee.setVisibility(View.GONE);
        department.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        nextInt = 8;
        continueScanning();
    };

    @Override
    public void onResume() {
        super.onResume();
        isFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFront = false;
    }

    private void signIn(String content) {
        try {
            long millis = System.currentTimeMillis();
            int index = String.valueOf(millis).length();
            String type = content.substring(0, 1);
            String employeeId = content.substring(1, content.length() - index);
            String timeString = content.substring(content.length() - index);
            long time = Long.parseLong(timeString);
            // 检查是否是今天扫的二维码
            ZonedDateTime currentDate = Instant.ofEpochMilli(millis)
                    .atZone(TimeZone.getTimeZone("GMT+8:00").toZoneId());
            ZonedDateTime signInTime = Instant.ofEpochMilli(time)
                    .atZone(TimeZone.getTimeZone("GMT+8:00").toZoneId());
            if (currentDate.getDayOfYear() == signInTime.getDayOfYear()) {
                post(employeeId, type);
            } else {
                Toast.makeText(this, "已过期的二维码", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "解析失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void post(final String employeeId, final String signInType) {
        Net.builder().method(Net.POST).handler(uiHandler)
                .url("/admin/employee/signin")
                .typeOf(SignInSuccess.class)
                .param("employee", employeeId)
                .param("type", signInType)
                .start(() -> progress("处理中..."))
                .end(this::dismissDialog)
                .success((Net.OnSuccess<SignInSuccess>) signInSuccess)
                .failure(signInFailure)
                .run();
    }

    @SuppressLint("SetTextI18n")
    private void continueScanning() {
        next.setText(nextInt + "s后继续");
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (--nextInt <= 0 || !isFront) {
                    status.setText("");
                    error.setText("");
                    type.setText("");
                    employee.setText("");
                    department.setText("");
                    date.setText("");
                    next.setText("");
                    if (nextInt <= 0) {
                        scanning.callOnClick();
                    }
                } else {
                    next.setText(nextInt + "s后继续");
                    uiHandler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}
