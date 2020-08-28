package com.lfq.mobileoffice.ui.home.message.roomapply;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.data.result.RoomApplyPager;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.net.Net;

/**
 * 消息中: 所有预约申请
 */
public class MeRAAllFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RoomApplyAdapter adapter;

    @Override
    public int getViewResource() {
        return R.layout.fragment_message_apply;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new RoomApplyAdapter(this);
        adapter.setOnLoadMoreListener(() -> {
            Api.roomApplyPage(map("pager")).success((Net.OnSuccess<RoomApplyPager>) roomApplyPager -> {
                adapter.getData().addAll(roomApplyPager.getData());
                adapter.notifyDataSetChanged();
                map("pager");
            }).run();
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        Api.roomApplyPage(null).success((Net.OnSuccess<RoomApplyPager>) roomApplyPager -> {
            boolean empty = roomApplyPager.getData().isEmpty();
            if (!empty) {
                adapter.setData(roomApplyPager.getData());
                adapter.notifyDataSetChanged();
            }
            get(R.id.recycler).setVisibility(empty ? View.GONE : View.VISIBLE);
            get(R.id.empty).setVisibility(empty ? View.VISIBLE : View.GONE);
            map("pager", roomApplyPager.getPager());
        }).run();
    }
}
