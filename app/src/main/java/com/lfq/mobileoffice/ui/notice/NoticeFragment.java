package com.lfq.mobileoffice.ui.notice;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.data.result.NoticePager;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.net.Api;
import com.lfq.mobileoffice.net.Net;

/**
 * 公告列表activity
 */
@LoggerName("公告")
public class NoticeFragment extends BaseFragment {
    private RecyclerAdapter adapter;

    @Override
    public int getViewResource() {
        return R.layout.fragment_notice;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // 初始化RecyclerView
        adapter = new RecyclerAdapter(this);

        // 点击某一项公告
        adapter.setOnItemClickListener((data, position) ->
                new NoticeDetailsActivity().start(requireContext(), data.getId())
        );

        // 上拉加载更多
        adapter.setOnLoadMoreListener(() -> {
            logger.info("加载更多");
            Api.noticesNextPage(map("pager"))
                    .success((Net.OnSuccess<NoticePager>) noticePager -> {
                        map("pager", noticePager.getPager());
                        adapter.getData().addAll(noticePager.getData());
                        adapter.notifyDataSetChanged();
                        logger.info("加载更多成功");
                    }).run();
        });

        // 初始化下拉刷新控件
        SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
            logger.info("开始重新加载");
            Api.noticesNextPage(null)
                    .success((Net.OnSuccess<NoticePager>) noticePager -> {
                        map("pager", noticePager.getPager());
                        adapter.setData(noticePager.getData());
                        adapter.notifyDataSetChanged();
                        swipe(false);
                        logger.info("重新加载成功");
                    }).run();
        };
        swipe().setOnRefreshListener(refreshListener);

        // 初始化加载
        refreshListener.onRefresh();
    }
}
