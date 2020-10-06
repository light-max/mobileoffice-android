package com.lfq.mobileoffice.ui.reimbursement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.ui.fileselectactivity.BaseFileSelectActivity;
import com.lfq.mobileoffice.data.request.BillItem;
import com.lfq.mobileoffice.data.request.ReimbursementPostData;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.ui.billitemselect.BillItemSelectActivity;
import com.lfq.mobileoffice.util.Utils;

/**
 * 报销申请activity
 */
public class ReimbursementActivity extends BaseFileSelectActivity {

    private BillItemListAdapter billItemListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        click(R.id.file, v -> selectFile());
        click(R.id.picture, v -> picture());
        click(R.id.post, v -> post());

        // 打开账单项目选择器
        click(R.id.add, v -> {
            Intent intent = new Intent(this, BillItemSelectActivity.class);
            startActivityForResult(intent, BillItemSelectActivity.REQUEST_CODE);
        });

        // 账单项目列表初始化
        billItemListAdapter = new BillItemListAdapter(this, R.id.bills);
        billItemListAdapter.setOnDeleteListener((adapter, position) -> {
            adapter.getData().remove(position);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_reimbursement;
    }

    /**
     * 提交请求
     */
    private void post() {
        ReimbursementPostData data = new ReimbursementPostData(
                Utils.string(text(R.id.payee)),
                Utils.string(text(R.id.card)),
                Utils.string(text(R.id.des)),
                getFileResourcesID(),
                billItemListAdapter.getData()
        );
        Api.postReimbursementApplication(data).success(o -> {
            text(R.id.post, "提交成功，等待管理员审核...");
            get(R.id.post).setEnabled(false);
            get(R.id.add).setEnabled(false);
            billItemListAdapter.setOnDeleteListener(null);
            disableSelected();
        }).run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (get(R.id.post).isEnabled()) {
            super.clearSelectFile();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 当选择了账单项目时的回调
        if (requestCode == BillItemSelectActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra("name");
                float amount = data.getFloatExtra("amount", 0);
                billItemListAdapter.getData().add(new BillItem(name, amount));
                billItemListAdapter.notifyDataSetChanged();
            }
        }
    }
}
