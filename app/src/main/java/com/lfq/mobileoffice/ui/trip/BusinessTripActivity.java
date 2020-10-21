package com.lfq.mobileoffice.ui.trip;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.util.Utils;
import com.lfq.mobileoffice.ui.fileselectactivity.BaseFileSelectActivity;
import com.lfq.mobileoffice.widget.DateTimeSelectedEditText;

import java.util.List;

/**
 * 出差申请activity
 *
 * @author 李凤强
 */
public class BusinessTripActivity extends BaseFileSelectActivity {

    private DateTimeSelectedEditText start;
    private DateTimeSelectedEditText end;

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

        click(R.id.file, v -> selectFile());
        click(R.id.picture, v -> picture());

        click(R.id.post, v -> {
            logger.info("pp");
            if (start.isSelected() && end.isSelected()) post();
        });
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_business_trip;
    }

    /**
     * 提交出差请求
     */
    private void post() {
        // 获取出差基本信息
        String des = Utils.string(text(R.id.des));
        String address = Utils.string(text(R.id.address));
        long startMillisecond = start.getMillisecond();
        long endMillisecond = end.getMillisecond();
        // 获取上传的文件
        List<String> resourcesID = super.getFileResourcesID();
        Api.postTravelApplication(des, address, startMillisecond,
                endMillisecond, resourcesID).success(o -> {
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
