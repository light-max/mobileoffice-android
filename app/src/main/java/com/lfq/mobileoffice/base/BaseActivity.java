package com.lfq.mobileoffice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.lfq.mobileoffice.logger.Logger;
import com.lfq.mobileoffice.util.ProgressDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 定制的activity
 */
public class BaseActivity extends AppCompatActivity implements Base {

    private AlertDialog progressDialog;
    private Map<Class<?>, ViewModel> viewModelMap;
    private Map<Object, Object> globalVariableMap;
    protected Logger logger;
    /*** 是否启用返回按钮 */
    private boolean enableBackButton = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = Logger.getLogger(this);
        globalVariableMap = new HashMap<>();
    }

    /**
     * 显示返回按钮
     */
    protected void useBackButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        enableBackButton = true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home && enableBackButton) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public <T extends View> T get(int viewId) {
        return findViewById(viewId);
    }

    @Override
    public Map<Object, Object> map() {
        return globalVariableMap;
    }

    @Override
    public void progress(String message, boolean cancelable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = ProgressDialog.create(this, message, cancelable);
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public <T extends ViewModel> T getModel(Class<T> tClass) {
        if (viewModelMap == null) {
            viewModelMap = new HashMap<>();
        }
        if (viewModelMap.get(tClass) == null) {
            viewModelMap.put(tClass, ViewModelProviders.of(this).get(tClass));
        }
        return (T) viewModelMap.get(tClass);
    }

    @Override
    public void startActivity(Class<?> tClass) {
        startActivity(new Intent(this, tClass));
    }
}
