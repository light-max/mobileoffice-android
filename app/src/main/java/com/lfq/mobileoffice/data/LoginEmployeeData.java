package com.lfq.mobileoffice.data;

import androidx.lifecycle.MutableLiveData;

import com.lfq.mobileoffice.data.result.Employee;

/**
 * 用户登陆数据
 */
public class LoginEmployeeData extends MutableLiveData<Employee> {
    private static LoginEmployeeData instance;

    private LoginEmployeeData() {
    }

    public static LoginEmployeeData getInstance() {
        if (instance == null) {
            instance = new LoginEmployeeData();
        }
        return instance;
    }
}
