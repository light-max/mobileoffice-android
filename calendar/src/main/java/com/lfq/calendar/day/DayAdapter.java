package com.lfq.calendar.day;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.calendar.R;
import com.lfq.mobileoffice.base.interfaces.KeyValue;
import com.lfq.mobileoffice.base.interfaces.ViewGet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期列表适配器
 *
 * @author 李凤强
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private List<LocalDate> list = new ArrayList<>();

    // 当天日期
    private final LocalDate currentDate = LocalDate
            .now(TimeZone.getTimeZone("GMT+8:00").toZoneId());

    // 当前所选择的日期
    private ViewHolder selected;

    private OnDateSelectedListener onDateSelectedListener;
    private OnDrawStatusListener onDrawStatusListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_day, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocalDate d = list.get(position);
        if (d == null) {
            return;
        }
        if (d.equals(currentDate)) {
            holder.day.setBackgroundResource(R.drawable.ic_current_date_background);
            holder.map("current", new Object());
        }
        if (onDateSelectedListener != null) {
            LocalDate currentSelectedDate = onDateSelectedListener.onGetCurrentSelectedDate();
            if (currentSelectedDate != null && currentSelectedDate.equals(d)) {
                holder.day.setBackgroundResource(R.drawable.ic_date_selected_background);
                selected = holder;
            }
        }
        holder.text(R.id.day, String.valueOf(d.getDayOfMonth()));
        if (onDrawStatusListener != null) {
            onDrawStatusListener.onDrawStatus(holder.status, d);
        }
        holder.itemView.setOnClickListener(v -> {
            if (selected != null) {
                if (selected.map("current") != null) {
                    selected.day.setBackgroundResource(R.drawable.ic_current_date_background);
                } else {
                    selected.day.setBackgroundResource(0);
                }
            }
            holder.day.setBackgroundResource(R.drawable.ic_date_selected_background);
            selected = holder;
            if (onDateSelectedListener != null) {
                onDateSelectedListener.onDateSelected(d);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public List<LocalDate> getList() {
        return list;
    }

    public void setList(List<LocalDate> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ViewGet, KeyValue {
        public final TextView day;
        public final TextView status;
        private Map<Object, Object> map;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = get(R.id.day);
            status = get(R.id.status);
        }

        @Override
        public <T extends View> T get(int viewId) {
            return itemView.findViewById(viewId);
        }

        @Override
        public Map<Object, Object> map() {
            if (map == null) {
                map = new HashMap<>();
            }
            return map;
        }
    }

    /**
     * 日期选择回调接口
     */
    public interface OnDateSelectedListener {
        /**
         * 当日期发生选择时调用这个回调函数
         *
         * @param date 选择的日期
         */
        void onDateSelected(LocalDate date);

        /**
         * 获取当前已选择的日期, 需要在视图刷新后继续设置为选择状态
         *
         * @return 返回当前选中的日期，有可能为空
         */
        @Nullable
        LocalDate onGetCurrentSelectedDate();
    }

    /**
     * 为适配器设置日期选择回调接口
     *
     * @param onDateSelectedListener {@link OnDateSelectedListener}
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    /**
     * 绘制考勤状态的接口
     */
    public interface OnDrawStatusListener {
        /**
         * 当绘制日期时就会调用这个回调接口，可以通过实现这个回调接口来绘制考勤的状态
         *
         * @param view 考勤状态{@link TextView}
         * @param date 当天的日期
         */
        void onDrawStatus(TextView view, LocalDate date);
    }

    /**
     * 为适配器设置绘制考勤状态的接口
     *
     * @param onDrawStatusListener {@link OnDrawStatusListener}
     */
    public void setOnDrawStatusListener(OnDrawStatusListener onDrawStatusListener) {
        this.onDrawStatusListener = onDrawStatusListener;
    }
}
