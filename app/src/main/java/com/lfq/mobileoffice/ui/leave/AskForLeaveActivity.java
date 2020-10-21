package com.lfq.mobileoffice.ui.leave;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.data.result.AFLType;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.util.Utils;
import com.lfq.mobileoffice.ui.fileselectactivity.BaseFileSelectActivity;
import com.lfq.mobileoffice.widget.DateTimeSelectedEditText;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 请假activity
 *
 * @author 李凤强
 */
public class AskForLeaveActivity extends BaseFileSelectActivity {

    private DateTimeSelectedEditText start;
    private DateTimeSelectedEditText end;
    private Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();

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

        click(R.id.file, v -> super.selectFile());
        click(R.id.picture, v -> super.picture());

        click(R.id.post, v -> {
            if (start.isSelected() && end.isSelected()) post();
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

    @Override
    protected int getViewResource() {
        return R.layout.activity_ask_for_leave;
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
        List<String> resources = super.getFileResourcesID();
        // 请假类型
        List<AFLType> aflTypes = map("aflTypes");
        AFLType type = aflTypes.get(spinner.getSelectedItemPosition());
        // 发送请求
        Api.postLeaveApplication(type.getId(), des, startMillisecond,
                endMillisecond, resources).success(o -> {
            text(R.id.post, "提交成功，等待管理员审核...");
            get(R.id.post).setEnabled(false);
            disableSelected();
        }).run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 如果退出activity时没有提交请假请求就把已上传的文件删除
        if (get(R.id.post).isEnabled()) {
            super.clearSelectFile();
        }
    }
}
