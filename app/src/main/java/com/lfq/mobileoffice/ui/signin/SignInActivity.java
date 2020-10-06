package com.lfq.mobileoffice.ui.signin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.LoginEmployeeData;
import com.lfq.mobileoffice.util.Utils;

/**
 * 扫描签到activity
 */
public class SignInActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_signin);
        int type = getIntent().getIntExtra("type", 0);
        ImageView img = get(R.id.qrcode);
        LoginEmployeeData.getInstance().observe(this, employee -> {
            String content = Utils.string(type) + employee.getId() + System.currentTimeMillis();
            Bitmap bitmap = QRCodeTools.createQRCodeBitmap(content, 600, 600);
            img.setImageBitmap(bitmap);
        });
    }

    /**
     * 上班签到启动器
     */
    public static void goToWork(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra("type", 1);
        context.startActivity(intent);
    }

    /**
     * 下班签到启动器
     */
    public static void offWork(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra("type", 2);
        context.startActivity(intent);
    }
}
