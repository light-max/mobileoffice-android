package com.lfq.mobileoffice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.base.BaseActivity;
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
                        finish();
                    })
                    .failure((e) -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivityForResult(intent, LoginActivity.LOGIN_REQUEST);
                        logger.info("自动登陆，登陆失败: " + e);
                    })
                    .run();
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, LoginActivity.LOGIN_REQUEST);
            }, 1500);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果调用登陆activity登陆成功
        if (requestCode == LoginActivity.LOGIN_REQUEST && resultCode == RESULT_OK) {
            // 启动主页activity
            startActivity(HomeActivity.class);
        }
        finish();
    }
}