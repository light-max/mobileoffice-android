package com.lfq.mobileoffice.ui.billitemselect;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.request.BillItem;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.util.Utils;

/**
 * 账单项目选择activity
 */
public class BillItemSelectActivity extends BaseActivity {

    public static final int REQUEST_CODE = 0x444;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_bill_item_select);
        text(R.id.amount).addTextChangedListener(watcher);
        click(R.id.post, v -> post());
    }

    /**
     * 选定这个账单项目，但需要发送请求检查是否符合规则
     */
    private void post() {
        String name = Utils.string(text(R.id.name));
        float amount;
        try {
            amount = Float.parseFloat(Utils.string(text(R.id.amount)));
        } catch (NumberFormatException e) {
            toast("请输入正确的金额");
            return;
        }
        Api.checkBillItem(new BillItem(name, amount)).success(o -> {
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("amount", amount);
            setResult(RESULT_OK, intent);
            finish();
        }).run();
    }

    // 限制输入两位小数
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String string = s.toString();
            String[] split = string.split("\\.");
            if (split.length > 1 && split[1].length() > 2) {
                text(R.id.amount, split[0] + "." + split[1].substring(0, 2));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
