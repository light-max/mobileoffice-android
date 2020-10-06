package com.lfq.mobileoffice.ui.reimbursement;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.request.BillItem;

/**
 * 账单项目列表适配器
 */
class BillItemListAdapter extends SimpleRecyclerAdapter<BillItem, BillItemListAdapter.ViewHolder> {

    private OnDeleteListener listener;

    public BillItemListAdapter(Base base, int recyclerViewId) {
        super(base, recyclerViewId);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_bill_item;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onBindViewHolder(ViewHolder holder, BillItem data) {
        holder.name.setText(data.getName());
        holder.amount.setText(String.format("%.2f", data.getAmount()));
        holder.delete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(this, holder.getAdapterPosition());
            }
        });
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView amount;
        private final TextView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    /**
     * 删除监听器
     */
    public interface OnDeleteListener {
        /**
         * 当点击item中的删除时就会调用这个方法
         *
         * @param adapter  当前适配器
         * @param position 点击的是第几项
         */
        void onDelete(BillItemListAdapter adapter, int position);
    }
}
