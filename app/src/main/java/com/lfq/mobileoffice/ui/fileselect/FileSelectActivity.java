package com.lfq.mobileoffice.ui.fileselect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 文件选择activity
 *
 * @author 李凤强
 */
public class FileSelectActivity extends BaseActivity {

    private FileListAdapter adapter;

    @SuppressLint("SdCardPath")
    private MutableLiveData<File> path = new MutableLiveData<File>() {{
        postValue(new File("/sdcard"));
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_file_select);
        adapter = new FileListAdapter(this);
        path.observe(this, file -> {
            File[] files = file.listFiles();
            assert files != null;
            List<File> list = Arrays.asList(files);
            adapter.setData(list);
            adapter.notifyDataSetChanged();
            text(R.id.path, file.getPath());
        });
        adapter.setOnItemClickListener((data, position) -> {
            if (data.isDirectory()) {
                path.postValue(data);
            } else {
                setResult(RESULT_OK,new Intent().setData(Uri.fromFile(data)));
                finish();
            }
        });
    }

    @SuppressLint("SdCardPath")
    @Override
    public void onBackPressed() {
        File file = Objects.requireNonNull(path.getValue());
        if (file.getPath().equals("/sdcard")) {
            super.onBackPressed();
        } else {
            path.postValue(file.getParentFile());
        }
    }
}
