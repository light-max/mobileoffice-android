package com.lfq.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.lfq.calendar.day.DayAdapter;
import com.lfq.calendar.month.MonthPagerAdapter;
import com.lfq.calendar.month.MonthView;
import com.lfq.mobileoffice.base.interfaces.KeyValue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤日历控件
 *
 * @author 李凤强
 */
public class AttendanceCalendarView extends LinearLayout implements ViewGetImpl, KeyValue {
    private OnDateSelectedListener onDateSelectedListener;
    private OnMonthSelectedListener onMonthSelectedListener;
    private OnDrawStatusListener onDrawStatusListener;
    private Map<Object, Object> map = new HashMap<>();

    public AttendanceCalendarView(Context context) {
        this(context, null, 0, 0);
    }

    public AttendanceCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public AttendanceCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AttendanceCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.view_calendar, this);
        ViewPager pager = get(R.id.pager);
        MonthPagerAdapter adapter = new MonthPagerAdapter(context);
        map("adapter", adapter);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener((SimpleOnPageChangeListener) position -> {
            adapter.selectMonth(position);
            List<MonthView> list = adapter.getList();
            LocalDate month = list.get(position % list.size()).getMonth();
            text(R.id.year, month.getYear() + "年");
            text(R.id.month, month.getMonthValue() + "月");
            if (onMonthSelectedListener != null) {
                onMonthSelectedListener.onMonthSelected(month);
            }
        });
        pager.setCurrentItem(adapter.getBegin(), false);
        // 上个月
        click(R.id.left, v -> pager.setCurrentItem(pager.getCurrentItem() - 1, false));
        // 下个月
        click(R.id.right, v -> pager.setCurrentItem(pager.getCurrentItem() + 1, false));
        // 设置日期选择的监听
        adapter.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(LocalDate date) {
                if (onDateSelectedListener != null) {
                    onDateSelectedListener.onDateSelected(date);
                }
                // 把当前选中的日期存入map中
                map("csd", date);
            }

            @Nullable
            @Override
            public LocalDate onGetCurrentSelectedDate() {
                // 返回当前选中的日期
                return map("csd");
            }
        });
        adapter.setOnDrawStatusListener((OnDrawStatusListener) (view, date) -> {
            if (onDrawStatusListener != null) {
                onDrawStatusListener.onDrawStatus(view, date);
            }
        });
    }

    /**
     * 手动更新视图
     */
    public void updateUI() {
        MonthPagerAdapter adapter = map("adapter");
        ViewPager pager = get(R.id.pager);
        List<MonthView> list = adapter.getList();
        int[] position = new int[]{
                pager.getCurrentItem() % list.size(),
                (pager.getCurrentItem() + list.size() - 1) % list.size(),
                (pager.getCurrentItem() + 1) % list.size()
        };
        for (int i : position) {
            list.get(i).drawMonth();
        }
    }

    /**
     * 手动更新视图
     *
     * @param month 按月份更新
     */
    public void updateUI(LocalDate month) {
        MonthPagerAdapter adapter = map("adapter");
        List<MonthView> list = adapter.getList();
        for (MonthView view : list) {
            if (month.equals(view.getMonth())) {
                view.drawMonth();
            }
        }
    }

    @Override
    public Map<Object, Object> map() {
        return map;
    }

    /**
     * 日期选择回调接口
     *
     * @see com.lfq.calendar.day.DayAdapter.OnDateSelectedListener
     */
    public interface OnDateSelectedListener extends DayAdapter.OnDateSelectedListener {
        /**
         * ignore
         */
        @Nullable
        @Override
        default LocalDate onGetCurrentSelectedDate() {
            return null;
        }
    }

    /**
     * 月份切换监听器
     */
    public interface OnMonthSelectedListener {
        /**
         * 当发生月份切换时就会调用这个回调接口
         *
         * @param month
         */
        void onMonthSelected(LocalDate month);
    }

    /**
     * 绘制考勤状态的监听器接口
     *
     * @see com.lfq.calendar.day.DayAdapter.OnDrawStatusListener
     */
    public interface OnDrawStatusListener extends DayAdapter.OnDrawStatusListener {
    }

    /**
     * 设置日期选择的回调接口
     *
     * @see MonthPagerAdapter#setOnDateSelectedListener(DayAdapter.OnDateSelectedListener)
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    /**
     * 设置月份切换的监听器
     *
     * @param onMonthSelectedListener {@link OnMonthSelectedListener}
     */
    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener) {
        this.onMonthSelectedListener = onMonthSelectedListener;
    }

    /**
     * 设置绘制考勤状态的回调接口
     *
     * @see MonthPagerAdapter#setOnDrawStatusListener(DayAdapter.OnDrawStatusListener)
     */
    public void setOnDrawStatusListener(OnDrawStatusListener onDrawStatusListener) {
        this.onDrawStatusListener = onDrawStatusListener;
    }
}
