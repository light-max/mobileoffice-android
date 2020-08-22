package com.lfq.mobileoffice.listener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * 上拉加载更多监听<br>
 * 仅用于{@link RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)}
 */
public class OnLoadMoreListener extends RecyclerView.OnScrollListener {
    // 用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    // "加载更多"的回调接口
    private Runnable runnable;

    /**
     * @param runnable "加载更多"的回调接口
     */
    public OnLoadMoreListener(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的itemPosition
            assert manager != null;
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                //加载更多
                if (runnable != null) {
                    runnable.run();
                }
            }
        }
    }

    @Override
    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
    }
}
