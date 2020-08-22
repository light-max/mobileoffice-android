package com.lfq.mobileoffice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.lfq.mobileoffice.logger.Logger;
import com.lfq.mobileoffice.util.ProgressDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 定制的fragment
 */
public class BaseFragment extends Fragment implements Base {

    private AlertDialog progressDialog;
    private Map<Class<?>, ViewModel> viewModelMap;
    private Map<Object, Object> globalVariableMap;
    protected Logger logger;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = Logger.getLogger(this);
        globalVariableMap = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getViewResource() == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            return inflater.inflate(getViewResource(), container, false);
        }
    }

    /**
     * 获取fragment的视图资源
     */
    public int getViewResource() {
        return 0;
    }

    @Override
    public <T extends View> T get(int viewId) {
        return Objects.requireNonNull(getView()).findViewById(viewId);
    }

    @Override
    public Map<Object, Object> map() {
        return globalVariableMap;
    }

    @Override
    public void toast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progress(String message, boolean cancelable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = ProgressDialog.create(requireActivity(), message, cancelable);
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
        startActivity(new Intent(requireActivity(), tClass));
    }
}
