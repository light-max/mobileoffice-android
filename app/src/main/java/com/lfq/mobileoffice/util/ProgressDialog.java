package com.lfq.mobileoffice.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.lfq.mobileoffice.R;

/**
 * 进度对话框工具类
 */
public class ProgressDialog {
    public static AlertDialog create(Context context,String message) {
        return create(context,message, false);
    }

    public static AlertDialog create(Context context,String message, boolean cancelable) {
        View view = View.inflate(context, R.layout.view_progress, null);
        ((TextView) view.findViewById(R.id.message)).setText(message);
        return new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(cancelable)
                .create();
    }
}
