package com.lfq.mobileoffice.ui.meinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.LoginEmployeeData;
import com.lfq.mobileoffice.data.result.Department;
import com.lfq.mobileoffice.data.result.Employee;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * “我的”信息activity
 */
public class MeInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_me_info);
        LoginEmployeeData.getInstance().observe(this, employee -> {
            text(R.id.name, employee.getName());
            text(R.id.sex, "male".equals(employee.getSex()) ? "男" : "女");
            text(R.id.id_number, employee.getIdNumber());
            Api.department(employee.getDepartment())
                    .success((Net.OnSuccess<Department>) department -> {
                        text(R.id.department, department.getName());
                    }).run();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY年MM月dd日");
            text(R.id.entry_date, dateFormat.format(new Date(employee.getCreateTime())));
            text(R.id.address, employee.getAddress());
            text(R.id.contact, employee.getContact());
        });
        click(R.id.save_address, v -> {
            Api.setAddress(Utils.string(text(R.id.address)))
                    .start(() -> progress("正在修改..."))
                    .end(this::dismissDialog)
                    .success((Net.OnSuccess<String>) s -> {
                        text(R.id.address, s);
                        Employee employee = LoginEmployeeData.getInstance().getValue();
                        if (employee != null) {
                            employee.setAddress(s);
                        }
                    }).run();
        });
        click(R.id.save_contact, v -> {
            Api.setContact(Utils.string(text(R.id.contact)))
                    .start(() -> progress("正在修改..."))
                    .end(this::dismissDialog)
                    .success((Net.OnSuccess<String>) s -> {
                        text(R.id.contact, s);
                        Employee employee = LoginEmployeeData.getInstance().getValue();
                        if (employee != null) {
                            employee.setContact(s);
                        }
                    }).run();
        });
    }
}
