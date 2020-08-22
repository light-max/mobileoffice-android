package com.lfq.mobileoffice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.ui.home.HomeActivity;
import com.lfq.mobileoffice.ui.login.LoginActivity;

import java.util.Objects;

/**
 * 主活动
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences login = getSharedPreferences("login", 0);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        // 判断是否是自动登陆
        if (login.getBoolean("auto", false)) {
            Api.login(login.getString("username", ""), login.getString("password", ""))
                    .start(() -> {
                        progress("正在登陆");
                        logger.info("自动登陆");
                    })
                    .end(this::dismissDialog)
                    .success(o -> {
                        startActivity(HomeActivity.class);
                        logger.info("自动登陆，登陆成功");
                    })
                    .failure((e) -> {
                        startActivity(LoginActivity.class);
                        logger.info("自动登陆，登陆失败: " + e);
                    })
                    .run();
        } else {
            new Handler().postDelayed(() -> {
                startActivity(LoginActivity.class);
                finish();
            }, 1500);
        }
    }
}