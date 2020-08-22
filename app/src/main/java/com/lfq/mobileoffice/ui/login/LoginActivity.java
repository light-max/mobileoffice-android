package com.lfq.mobileoffice.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.ui.home.HomeActivity;
import com.lfq.mobileoffice.util.Utils;

/**
 * 登陆界面activity
 */
@LoggerName("登陆")
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("login", 0);
        setContentView(R.layout.activity_login);

        // 查找控件
        EditText id = get(R.id.id);
        EditText pwd = get(R.id.pwd);
        CheckBox remember = get(R.id.remember);
        CheckBox auto = get(R.id.auto);

        // 初始化控件内容
        if (preferences.getBoolean("remember", false)) {
            id.setText(preferences.getString("username", ""));
            pwd.setText(preferences.getString("password", ""));
        }
        remember.setChecked(preferences.getBoolean("remember", false));
        auto.setChecked(preferences.getBoolean("auto", false));

        // 绑定记住密码选项的监听事件，取消记住密码的同时也取消自动登陆
        checkChange(remember, (buttonView, isChecked) -> {
            if (!isChecked) auto.setChecked(false);
        });
        // 绑定自动登陆选项的监听事件，开启自动登陆的同时也开启记住密码
        checkChange(auto, (buttonView, isChecked) -> {
            if (isChecked) remember.setChecked(true);
        });

        // 绑定登陆按钮的监听事件，点击登陆按钮进行登陆
        click(R.id.post, (view) -> Api
                .login(Utils.string(id), Utils.string(pwd))
                .start(() -> {
                    progress("正在登陆");
                    logger.info("登陆");
                })
                .end(this::dismissDialog)
                .success((object) -> {
                    // 登陆成功后把控件内容保存在缓存中
                    preferences.edit().putString("username", Utils.string(id))
                            .putString("password", Utils.string(pwd))
                            .putBoolean("remember", remember.isChecked())
                            .putBoolean("auto", auto.isChecked())
                            .apply();
                    // 启动主页activity
                    startActivity(HomeActivity.class);
                    logger.info("登陆成功");
                })
                .failure((e) -> logger.info("登陆失败: " + e))
                .run()
        );
    }
}
