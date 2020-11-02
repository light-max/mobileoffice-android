package com.lfq.calendar.month;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.calendar.R;
import com.lfq.calendar.ViewGetImpl;
import com.lfq.calendar.day.DayAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示每个月日期的控件
 *
 * @author 李凤强
 */
public class MonthView extends LinearLayout implements ViewGetImpl {
    private LocalDate month;
    private final RecyclerView recyclerView;
    private final DayAdapter adapter;

    public MonthView(Context context) {
        super(context);
        View.inflate(context, R.layout.view_month, this);
        adapter = new DayAdapter();
        recyclerView = get(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 7));
        recyclerView.setAdapter(adapter);
    }

    public void drawMonth() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = month;
        // 这个月的星期对应的空的日期用null填充
        int empty = month.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < empty; i++) {
            dates.add(null);
        }
        // 获取这个月每一天的日期
        while (current.getMonthValue() == month.getMonthValue()) {
            dates.add(current);
            current = current.plusDays(1);
        }
        adapter.setList(dates);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置月份
     *
     * @param month 包含了年份与月份的LocalDate对象
     */
    public void setMonth(LocalDate month) {
        this.month = LocalDate.of(month.getYear(), month.getMonthValue(), 1);
        drawMonth();
    }

    public LocalDate getMonth() {
        return month;
    }

    /**
     * @see DayAdapter#setOnDateSelectedListener(DayAdapter.OnDateSelectedListener)
     */
    public void setOnDateSelectedListener(DayAdapter.OnDateSelectedListener onDateSelectedListener) {
        if (recyclerView.getAdapter() instanceof DayAdapter) {
            ((DayAdapter) recyclerView.getAdapter()).setOnDateSelectedListener(onDateSelectedListener);
        }
    }

    /**
     * @see DayAdapter#setOnDrawStatusListener(DayAdapter.OnDrawStatusListener)
     */
    public void setOnDrawStatusListener(DayAdapter.OnDrawStatusListener onDrawStatusListener) {
        if (recyclerView.getAdapter() instanceof DayAdapter) {
            ((DayAdapter) recyclerView.getAdapter()).setOnDrawStatusListener(onDrawStatusListener);
        }
    }
}
