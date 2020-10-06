package com.lfq.mobileoffice.scan.data;

import androidx.lifecycle.MutableLiveData;

import com.lfq.mobileoffice.scan.data.result.Admin;

public class LoginDataInstance extends MutableLiveData<Admin> {
    private static LoginDataInstance instance;

    public static LoginDataInstance getInstance() {
        if (instance == null) {
            instance = new LoginDataInstance();
        }
        return instance;
    }
}
