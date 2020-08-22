package com.lfq.mobileoffice.ui.home.room;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.data.result.RoomPager;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.room.RoomDetailsActivity;

@LoggerName("会议室")
public class RoomFragment extends BaseFragment {

    private RecyclerAdapter adapter;

    @Override
    public int getViewResource() {
        return R.layout.fragment_room;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // 初始化RecyclerView
        adapter = new RecyclerAdapter(this);

        // 设置下拉刷新事件
        SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
            logger.info("重新加载");
            Api.roomsNextPage(null)
                    .end(() -> swipe(false))
                    .success((Net.OnSuccess<RoomPager>) roomPager -> {
                        adapter.setData(roomPager.getData());
                        adapter.notifyDataSetChanged();
                        map("pager", roomPager.getPager());
                        logger.info("重新加载成功");
                    }).run();
        };
        swipe(refreshListener);

        // 设置上拉加载更多
        adapter.setOnLoadMoreListener(() -> {
            logger.info("加载更多");
            Api.roomsNextPage(map("pager"))
                    .success((Net.OnSuccess<RoomPager>) roomPager -> {
                        if (!roomPager.getData().isEmpty()) {
                            adapter.getData().addAll(roomPager.getData());
                            adapter.notifyDataSetChanged();
                            map("pager", roomPager.getPager());
                        }
                        logger.info("加载更多成功");
                    }).run();
        });

        // 设置点击事件
        adapter.setOnItemClickListener((data, position) ->
                new RoomDetailsActivity().start(requireActivity(), data.getId())
        );

        // 初始化加载
        refreshListener.onRefresh();
    }
}
