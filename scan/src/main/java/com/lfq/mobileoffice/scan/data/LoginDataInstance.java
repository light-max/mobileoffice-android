package com.lfq.mobileoffice.scan.data;

import androidx.lifecycle.MutableLiveData;

import com.lfq.mobileoffice.scan.data.result.Admin;

/**
 * 管理员登陆成功的数据
 *
 * @author 李凤强
 */
public class LoginDataInstance extends MutableLiveData<Admin> {
    private static LoginDataInstance instance;

    public static LoginDataInstance getInstance() {
        if (instance == null) {
            instance = new LoginDataInstance();
        }
        return instance;
    }
}
