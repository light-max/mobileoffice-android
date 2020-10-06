package com.lfq.mobileoffice.ui.home.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.interfaces.ViewGet;

/**
 * 有图标的按钮
 */
public class IconButton extends LinearLayout implements ViewGet {
    public IconButton(Context context) {
        this(context, null, 0, 0);
    }

    public IconButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public IconButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("Recycle")
    public IconButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.view_icon_button, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IconButton);
        Drawable icon = array.getDrawable(R.styleable.IconButton_bt_icon);
        String text = array.getString(R.styleable.IconButton_bt_text);
        ((ImageView) get(R.id.icon)).setImageDrawable(icon);
        text(R.id.text, text);
    }

    @Override
    public <T extends View> T get(int viewId) {
        return findViewById(viewId);
    }
}
