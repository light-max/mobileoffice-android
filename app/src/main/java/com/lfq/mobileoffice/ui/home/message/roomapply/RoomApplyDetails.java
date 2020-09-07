package com.lfq.mobileoffice.ui.home.message.roomapply;

import android.content.Context;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.data.result.Employee;
import com.lfq.mobileoffice.data.result.RoomApply;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.util.Utils;

import java.util.Map;

class RoomApplyDetails {
    /**
     * 打开对话框，显示预约详情
     *
     * @param map 状态的文字颜色与文字
     */
    static void showDialog(Map<Integer, Pair<Integer, String>> map, Context context, RoomApply o) {
        String dateFormat = "YYYY/MM/dd HH:mm";
        View view = View.inflate(context, R.layout.view_room_apply_details, null);
        TextView start = view.findViewById(R.id.start);
        TextView end = view.findViewById(R.id.end);
        TextView status = view.findViewById(R.id.status);
        TextView des = view.findViewById(R.id.des);
        TextView createTime = view.findViewById(R.id.create_time);

        start.setText(Utils.dateFormat(dateFormat, o.getStart()));
        end.setText(Utils.dateFormat(dateFormat, o.getEnd()));
        Pair<Integer, String> pair = map.get(o.getStatus());
        if (pair != null) {
            status.setTextColor(pair.first);
            status.setText(pair.second);
        }
        des.setText(o.getDes());
        createTime.setText(Utils.dateFormat(dateFormat, o.getCreateTime()));

        new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("我知道了", null)
                .show();
        MutableLiveData<Employee> employeeMutableLiveData = new MutableLiveData<>();
        Net.builder().method(Net.GET)
                .url("/{token}/login")
                .path("token", "admin")
                .param("account", "admin")
                .param("pwd", "123456")
                .handler(new Handler())
                .success((Net.OnSuccess<Employee>) employee -> {

                })
                .failure(message -> {

                })
                .run();

    }
}
