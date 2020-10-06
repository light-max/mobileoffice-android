package com.lfq.mobileoffice.ui.bills;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.adapter.SimpleRecyclerAdapter;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.BillItem;

/**
 * 账单项目列表适配器
 */
class BillsListAdapter extends SimpleRecyclerAdapter<BillItem, BillsListAdapter.ViewHolder> {

    public BillsListAdapter(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_bill_item_1;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onBindViewHolder(ViewHolder holder, BillItem data) {
        holder.name.setText(data.getName());
        holder.amount.setText(String.format("%.2f", data.getAmount()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final EditText name;
        private final EditText amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
