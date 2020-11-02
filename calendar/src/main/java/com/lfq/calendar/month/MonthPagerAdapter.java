package com.lfq.calendar.month;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lfq.calendar.day.DayAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * 月份ViewPage的适配器<br>
 * 是{@link MonthView}的容器
 */
public class MonthPagerAdapter extends PagerAdapter {
    private List<MonthView> list = new ArrayList<>();
    private int begin;

    public MonthPagerAdapter(Context context) {
        LocalDate date = LocalDate.now(TimeZone.getTimeZone("GMT+8:00").toZoneId());
        list.add(new MonthView(context));
        list.add(new MonthView(context));
        list.add(new MonthView(context));
        list.add(new MonthView(context));
        list.get(0).setMonth(date);
        list.get(1).setMonth(date.plusMonths(1));
        list.get(3).setMonth(date.minusMonths(1));
        begin = Short.MAX_VALUE / 2 / list.size() * list.size();
    }

    @Override
    public int getCount() {
        return Short.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        MonthView child = list.get(position % list.size());
        container.addView(child);
        return child;
    }

    /**
     * 当发生了月份选择后应该调用此方法为前后两个视图设置新的日期<br>
     * <b>需要在{@link ViewPager.OnPageChangeListener#onPageSelected(int)}中调用</b>
     *
     * @param position 当前选择的项
     */
    public void selectMonth(int position) {
        position %= list.size();
        LocalDate current = list.get(position).getMonth();
        list.get((position + 1) % list.size()).setMonth(current.plusMonths(1));
        if (position - 1 == -1) {
            list.get(list.size() - 1).setMonth(current.minusMonths(1));
        } else {
            list.get(position - 1).setMonth(current.minusMonths(1));
        }
    }

    public int getBegin() {
        return begin;
    }

    public List<MonthView> getList() {
        return list;
    }

    /**
     * @see MonthView#setOnDateSelectedListener(DayAdapter.OnDateSelectedListener)
     */
    public void setOnDateSelectedListener(DayAdapter.OnDateSelectedListener onDateSelectedListener) {
        for (MonthView view : list) {
            view.setOnDateSelectedListener(onDateSelectedListener);
        }
    }

    /**
     * @see MonthView#setOnDrawStatusListener(DayAdapter.OnDrawStatusListener)
     */
    public void setOnDrawStatusListener(DayAdapter.OnDrawStatusListener onDrawStatusListener) {
        for (MonthView view : list) {
            view.setOnDrawStatusListener(onDrawStatusListener);
        }
    }
}
