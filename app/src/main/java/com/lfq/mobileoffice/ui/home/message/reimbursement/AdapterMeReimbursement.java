package com.lfq.mobileoffice.ui.home.message.reimbursement;

import android.annotation.SuppressLint;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.Base;
import com.lfq.mobileoffice.data.result.Reimbursement;
import com.lfq.mobileoffice.ui.home.message.MessageDetails;
import com.lfq.mobileoffice.ui.home.message.StatusAdapter;

/**
 * 报销记录适配器
 */
public class AdapterMeReimbursement extends StatusAdapter<Reimbursement, AdapterMeReimbursement.ViewHolder> {

    public AdapterMeReimbursement(Base base) {
        super(base);
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_reimbursement;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(ViewHolder holder, Reimbursement data) {
        holder.payeeName.setText("收款人：" + data.getPayeeName());
        holder.bankCard.setText("收款账号：" + data.getBankCard());
        holder.total.setText("总金额：" + data.totalString());
        holder.des.setText("备注：" + data.getDes());
        Pair<Integer, String> status = this.status.get(data.getStatus());
        if (status != null) {
            holder.status.setText(status.second);
            holder.status.setTextColor(status.first);
        }
    }

    @Override
    public void onItemClick(Reimbursement data, int position) {
        MessageDetails.reimbursement(status, base.getContext(), data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView payeeName;
        private final TextView bankCard;
        private final TextView total;
        private final TextView des;
        private final TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            payeeName = itemView.findViewById(R.id.payeeName);
            bankCard = itemView.findViewById(R.id.bankCard);
            total = itemView.findViewById(R.id.total);
            des = itemView.findViewById(R.id.des);
            status = itemView.findViewById(R.id.status);
        }
    }
}
