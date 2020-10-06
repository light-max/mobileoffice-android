package com.lfq.mobileoffice.widget;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 时间日期选择器编辑框
 */
public class DateTimeSelectedEditText extends LinearLayout {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText time;
    private EditText date;
    private OnChangeListener onChangeListener;

    public DateTimeSelectedEditText(Context context) {
        this(context, null, 0, 0);
    }

    public DateTimeSelectedEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DateTimeSelectedEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("DefaultLocale")
    public DateTimeSelectedEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.view_date_time_edit, this);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        datePickerDialog = new DatePickerDialog(context);

        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            date.setText(String.format("%d/%d/%d", year, month + 1, dayOfMonth));
            if (isSelected() && onChangeListener != null) {
                onChangeListener.onChange(getMillisecond());
            }
        });
        timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            time.setText(String.format("%d:%d", hourOfDay, minute));
            time.setTag(view);
            if (isSelected() && onChangeListener != null) {
                onChangeListener.onChange(getMillisecond());
            }
        }, 0, 0, true);

        date.setOnClickListener(v -> {
            datePickerDialog.show();
        });
        time.setOnClickListener(v -> {
            timePickerDialog.show();
        });
    }

    /**
     * 是否已经选择了时间
     */
    public boolean isSelected() {
        return date.getText().length() > 0 && time.getText().length() > 0;
    }

    /**
     * 获取已选择的时间，单位毫秒
     */
    public long getMillisecond() {
        DatePicker datePicker = datePickerDialog.getDatePicker();
        TimePicker timePicker = (TimePicker) time.getTag();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getHour(),
                timePicker.getMinute()
        );
        // 60*1000毫秒=1分钟
        int minute = 60 * 1000;
        // 最小单位只取分钟，秒和毫秒都舍弃
        return calendar.getTimeInMillis() / minute * minute;
    }

    /**
     * 清除已选择的时间
     */
    public void clearTime() {
        date.setText("");
        time.setText("");
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    /**
     * 时间选择回调监听接口
     */
    public interface OnChangeListener {
        /**
         * 当时间或日期发生改变时就会回调这个函数
         *
         * @param millis 当前时间，单位毫秒
         */
        void onChange(long millis);
    }
}
