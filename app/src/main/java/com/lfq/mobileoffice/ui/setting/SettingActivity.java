package com.lfq.mobileoffice.ui.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.net.Net;

public class SettingActivity extends BaseActivity {

    public static final int REQUEST_CODE = 0x33;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_setting);
        click(R.id.logout, v -> logout());
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setMessage("你确定要退出登陆吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    Net.builder().method(Net.POST).url("/employee/logout").run();
                    setResult(RESULT_OK);
                    finish();
                }).show();
    }
}
