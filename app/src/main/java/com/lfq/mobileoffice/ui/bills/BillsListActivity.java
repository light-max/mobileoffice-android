package com.lfq.mobileoffice.ui.bills;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.result.BillItem;
import com.lfq.mobileoffice.net.Net;

import java.util.List;

/**
 * 报销账单列表activity<br>
 * <b>使用{@link #start(Context, long)}方法来启动此activity</b>
 */
public class BillsListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_resource_list);
        Intent intent = getIntent();
        long reimbursementId = intent.getLongExtra("reimbursementId", 0);
        if (reimbursementId <= 0) {
            RuntimeException exception = new RuntimeException("请设置大于0的reimbursementId");
            logger.error(exception);
            throw exception;
        }

        BillsListAdapter adapter = new BillsListAdapter(this);

        Net.builder().method(Net.GET).handler(new Handler()).typeListOf(BillItem.class)
                .url("/reimbursement/bills/{reimbursementId}")
                .path("reimbursementId", reimbursementId)
                .success((Net.OnSuccess<List<BillItem>>) billItems -> {
                    boolean empty = billItems.isEmpty();
                    if (!empty) {
                        adapter.setData(billItems);
                        adapter.notifyDataSetChanged();
                    }
                    adapter.getView().setVisibility(empty ? View.GONE : View.VISIBLE);
                    get(R.id.empty).setVisibility(empty ? View.VISIBLE : View.GONE);
                }).run();
    }

    /**
     * 启动方法
     *
     * @param context         上下文
     * @param reimbursementId 报销记录id
     */
    public static void start(Context context, long reimbursementId) {
        Intent intent = new Intent(context, BillsListActivity.class);
        intent.putExtra("reimbursementId", reimbursementId);
        context.startActivity(intent);
    }
}
