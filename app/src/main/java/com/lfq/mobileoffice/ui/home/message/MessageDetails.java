package com.lfq.mobileoffice.ui.home.message;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.data.result.Leave;
import com.lfq.mobileoffice.data.result.Reimbursement;
import com.lfq.mobileoffice.data.result.RoomApply;
import com.lfq.mobileoffice.data.result.Travel;
import com.lfq.mobileoffice.ui.bills.BillsListActivity;
import com.lfq.mobileoffice.ui.resources.ResourceListActivity;
import com.lfq.mobileoffice.util.Utils;

import java.util.Map;

/**
 * 查看消息详情的工具类
 *
 * @author 李凤强
 */
public class MessageDetails {

    private static final String dateFormat = "YYYY/MM/dd HH:mm";

    /**
     * 打开对话框，显示预约详情
     *
     * @param map 状态的文字颜色与文字
     */
    public static void roomApply(Map<Integer, Pair<Integer, String>> map, Context context, RoomApply o) {
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
    }

    /**
     * 打开对话框，显示请假详情
     *
     * @param map
     * @param context
     * @param o
     */
    public static void leave(Map<Integer, Pair<Integer, String>> map, Context context, Leave o) {
        View view = View.inflate(context, R.layout.view_leave_details, null);
        TextView start = view.findViewById(R.id.start);
        TextView end = view.findViewById(R.id.end);
        TextView status = view.findViewById(R.id.status);
        TextView typeName = view.findViewById(R.id.typeName);
        TextView des = view.findViewById(R.id.des);
        TextView createTime = view.findViewById(R.id.create_time);
        TextView resourceCount = view.findViewById(R.id.resourceCount);

        start.setText(Utils.dateFormat(dateFormat, o.getStart()));
        end.setText(Utils.dateFormat(dateFormat, o.getEnd()));
        Pair<Integer, String> pair = map.get(o.getStatus());
        if (pair != null) {
            status.setTextColor(pair.first);
            status.setText(pair.second);
        }
        typeName.setText(o.getTypeName());
        des.setText(o.getDes());
        createTime.setText(Utils.dateFormat(dateFormat, o.getCreateTime()));
        if (o.getResources().isEmpty()) {
            ((View) resourceCount.getParent()).setVisibility(View.GONE);
        } else {
            resourceCount.setText(String.valueOf(o.getResources().size()));
            view.findViewById(R.id.open).setOnClickListener(v -> {
                ResourceListActivity.start(context, "leave/file/{targetId}", o.getId());
            });
        }
        new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("我知道了", null)
                .show();
    }

    public static void travel(Map<Integer, Pair<Integer, String>> map, Context context, Travel o) {
        View view = View.inflate(context, R.layout.view_travel_details, null);
        TextView start = view.findViewById(R.id.start);
        TextView end = view.findViewById(R.id.end);
        TextView status = view.findViewById(R.id.status);
        TextView address = view.findViewById(R.id.address);
        TextView des = view.findViewById(R.id.des);
        TextView createTime = view.findViewById(R.id.create_time);
        TextView resourceCount = view.findViewById(R.id.resourceCount);

        start.setText(Utils.dateFormat(dateFormat, o.getStart()));
        end.setText(Utils.dateFormat(dateFormat, o.getEnd()));
        Pair<Integer, String> pair = map.get(o.getStatus());
        if (pair != null) {
            status.setTextColor(pair.first);
            status.setText(pair.second);
        }
        address.setText(o.getAddress());
        des.setText(o.getDes());
        createTime.setText(Utils.dateFormat(dateFormat, o.getCreateTime()));
        if (o.getResources().isEmpty()) {
            ((View) resourceCount.getParent()).setVisibility(View.GONE);
        } else {
            resourceCount.setText(String.valueOf(o.getResources().size()));
            view.findViewById(R.id.open).setOnClickListener(v -> {
                ResourceListActivity.start(context, "travel/file/{targetId}", o.getId());
            });
        }
        new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("我知道了", null)
                .show();
    }

    public static void reimbursement(Map<Integer, Pair<Integer, String>> map, Context context, Reimbursement o) {
        View view = View.inflate(context, R.layout.view_reimbursement_details, null);
        TextView total = view.findViewById(R.id.total);
        TextView payeeName = view.findViewById(R.id.payeeName);
        TextView bankCard = view.findViewById(R.id.bankCard);
        TextView status = view.findViewById(R.id.status);
        TextView des = view.findViewById(R.id.des);
        TextView createTime = view.findViewById(R.id.create_time);
        TextView resourceCount = view.findViewById(R.id.resourceCount);
        TextView billsCount = view.findViewById(R.id.billsCount);

        total.setText(o.totalString());
        payeeName.setText(o.getPayeeName());
        bankCard.setText(o.getBankCard());
        Pair<Integer, String> pair = map.get(o.getStatus());
        if (pair != null) {
            status.setTextColor(pair.first);
            status.setText(pair.second);
        }
        des.setText(o.getDes());
        createTime.setText(Utils.dateFormat(dateFormat, o.getCreateTime()));
        if (o.getResources().isEmpty()) {
            ((View) resourceCount.getParent()).setVisibility(View.GONE);
        } else {
            resourceCount.setText(String.valueOf(o.getResources().size()));
            view.findViewById(R.id.r_open).setOnClickListener(v -> {
                ResourceListActivity.start(context, "reimbursement/file/{targetId}", o.getId());
            });
        }
        if (o.getBillItems().isEmpty()) {
            ((View) billsCount.getParent()).setVisibility(View.GONE);
        } else {
            billsCount.setText(String.valueOf(o.getBillItems().size()));
            view.findViewById(R.id.b_open).setOnClickListener(v -> {
                BillsListActivity.start(context, o.getId());
            });
        }
        new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("我知道了", null)
                .show();
    }
}
