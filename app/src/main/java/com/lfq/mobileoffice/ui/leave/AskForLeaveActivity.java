package com.lfq.mobileoffice.ui.leave;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.result.AFLType;
import com.lfq.mobileoffice.data.result.Resource;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.fileselect.FileSelectActivity;
import com.lfq.mobileoffice.util.DateTimeSelectedEditText;
import com.lfq.mobileoffice.util.FileUtil;
import com.lfq.mobileoffice.util.PermissionUtil;
import com.lfq.mobileoffice.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 请假activity
 */
public class AskForLeaveActivity extends BaseActivity {

    private DateTimeSelectedEditText start;
    private DateTimeSelectedEditText end;
    private FileListAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_ask_for_leave);

        adapter = new FileListAdapter(this);
        // 生成已上传的文件
        adapter.setOnDeleteListener((position, file) -> {
            adapter.getData().remove(file);
            adapter.notifyDataSetChanged();
            Api.fileDelete(map(file)).run();
            map(file, null);
        });

        start = get(R.id.start);
        end = get(R.id.end);

        // 为时间选择器设置时间选择的监听
        DateTimeSelectedEditText.OnChangeListener listener = millis -> {
            if (start.isSelected() && end.isSelected()) {
                long start = this.start.getMillisecond();
                long end = this.end.getMillisecond();
                long minute = (end - start) / 1000 / 60;
                if (minute >= 60 * 24 * 7) {
                    String format = String.format("%d周%d日", minute / 60 / 24 / 7, minute / 60 / 24 % 7);
                    text(R.id.duration, format);
                } else if (minute >= 60 * 24) {
                    String format = String.format("%d天%d小时", minute / 60 / 24, minute / 60 % 24);
                    text(R.id.duration, format);
                } else if (minute >= 60) {
                    String format = String.format("%d小时%d分钟", minute / 60, minute % 60);
                    text(R.id.duration, format);
                } else {
                    text(R.id.duration, minute + "分钟");
                }
            }
        };
        start.setOnChangeListener(listener);
        end.setOnChangeListener(listener);

        click(R.id.file, v -> selectFile());
        click(R.id.picture, v -> picture());

        click(R.id.post, v -> {
            if (start.isSelected()) post();
        });

        // 查询请假类型
        spinner = get(R.id.spinner);
        Api.aflTypes().success((Net.OnSuccess<List<AFLType>>) aflTypes -> {
            map("aflTypes", aflTypes);
            List<String> items = aflTypes.stream().map(AFLType::getName).collect(Collectors.toList());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }).run();
    }

    /**
     * 提交请求
     */
    private void post() {
        // 开始时间，结束时间，请假描述
        long startMillisecond = start.getMillisecond();
        long endMillisecond = end.getMillisecond();
        String des = Utils.string(text(R.id.des));
        // 上传的文件
        List<String> resources = new ArrayList<>();
        for (File datum : adapter.getData()) {
            String resourceId = ((Resource) map(datum)).getId();
            resources.add(resourceId);
        }
        // 请假类型
        List<AFLType> aflTypes = map("aflTypes");
        AFLType type = aflTypes.get(spinner.getSelectedItemPosition());
        // 发送请求
        Api.postLeaveApplication(type.getId(), des, startMillisecond,
                endMillisecond, resources).success(o -> {
            text(R.id.post, "提交成功，等待管理员审核...");
            get(R.id.post).setEnabled(false);
            get(R.id.file).setEnabled(false);
            get(R.id.picture).setEnabled(false);
            get(R.id.recycler).setEnabled(false);
            adapter.setOnDeleteListener(null);
        }).run();
    }

    /**
     * 上传文件
     */
    private void upload(File file) {
        Api.fileUpload(file).end(this::dismissDialog).start(() -> progress("正在上传文件"))
                .success((Net.OnSuccess<Resource>) resource -> {
                    adapter.getData().add(file);
                    adapter.notifyDataSetChanged();
                    map(file, resource);
                }).run();
    }

    private final int FILE = 0x2;
    private final int PICTURE = 0x3;

    /**
     * 选择文件
     */
    private void selectFile() {
        if (PermissionUtil.localStorage(this)) {
            Intent intent = new Intent(this, FileSelectActivity.class);
            startActivityForResult(intent, FILE);
        } else {
            toast("请授权文件访问权限");
        }
    }

    /**
     * 选择照片
     */
    private void picture() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PICTURE);
    }

    @SuppressLint("Recycle")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        // 选择文件
        if (requestCode == FILE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            assert uri != null;
            File file = new File(Objects.requireNonNull(uri.getPath()));
            upload(file);
        }
        // 选择照片
        else if (requestCode == PICTURE) {
            Uri uri = data.getData();
            assert uri != null;
            String file = FileUtil.getFilePathByUri(this, uri);
            upload(new File(file));
        }
    }
}
