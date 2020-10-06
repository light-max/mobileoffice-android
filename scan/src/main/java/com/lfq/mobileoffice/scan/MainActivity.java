package com.lfq.mobileoffice.scan;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.scan.data.LoginDataInstance;
import com.lfq.mobileoffice.scan.data.result.Admin;

/**
 * 入口，登陆activity
 */
public class MainActivity extends BaseActivity {

    private SharedPreferences preferences;
    private EditText username;
    private EditText password;
    private CheckBox auto;
    private CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getPreferences(0);
        setContentView(R.layout.activity_main);

        username = get(R.id.username);
        password = get(R.id.password);
        auto = get(R.id.auto);
        remember = get(R.id.remember);

        username.setText(preferences.getString("username", ""));
        auto.setChecked(preferences.getBoolean("auto", false));
        remember.setChecked(preferences.getBoolean("remember", false));

        checkChange(auto, (buttonView, isChecked) -> {
            if (isChecked) {
                remember.setChecked(true);
            }
        });
        checkChange(remember, (buttonView, isChecked) -> {
            if (!isChecked) {
                auto.setChecked(false);
            }
        });
        click(R.id.login, v -> {
            Net.builder().method(Net.POST).typeOf(Admin.class)
                    .handler(new Handler())
                    .url("/admin/login")
                    .param("username", username.getText())
                    .param("pwd", password.getText())
                    .start(() -> progress("登陆中..."))
                    .end(this::dismissDialog)
                    .success((Net.OnSuccess<Admin>) loginSuccess)
                    .run();
        });

        // 记住密码
        if (remember.isChecked()) {
            password.setText(preferences.getString("password", ""));
        }
        // 自动登陆
        if (auto.isChecked()) {
            get(R.id.login).callOnClick();
        }
    }

    private Net.OnSuccess<Admin> loginSuccess = admin -> {
        LoginDataInstance.getInstance().postValue(admin);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor edit = preferences.edit()
                .putString("username", username.getText().toString())
                .putBoolean("auto", auto.isChecked())
                .putBoolean("remember", remember.isChecked());
        if (remember.isChecked()) {
            edit.putString("password", password.getText().toString());
        }
        edit.apply();
        startActivity(ScanningActivity.class);
        finish();
    };
}