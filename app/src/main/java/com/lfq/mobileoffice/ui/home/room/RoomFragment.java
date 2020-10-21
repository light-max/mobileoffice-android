package com.lfq.mobileoffice.ui.home.room;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.data.result.Pager;
import com.lfq.mobileoffice.data.result.RoomPager;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.room.RoomDetailsActivity;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.util.Utils;
import com.lfq.mobileoffice.widget.DateTimeSelectedEditText;

/**
 * 会议室列表fragment
 *
 * @author 李凤强
 */
@LoggerName("会议室")
public class RoomFragment extends BaseFragment {

    private RoomAdapter adapter;
    private DateTimeSelectedEditText start;
    private DateTimeSelectedEditText end;

    private final String CAPACITY = "capacity";
    private final String START = "start";
    private final String END = "end";
    private final String PAGER = "pager";
    private final String NAME = "name";
    private EditText name;

    @Override
    public int getViewResource() {
        return R.layout.fragment_room;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        start = get(R.id.start);
        end = get(R.id.end);
        name = get(R.id.name);
        // 打开抽屉
        click(R.id.filter, v -> {
            DrawerLayout drawer = get(R.id.drawer);
            drawer.openDrawer(GravityCompat.END);
            closeKeyBord();
        });
        // 关闭抽屉
        click(R.id.close, v -> {
            DrawerLayout drawer = get(R.id.drawer);
            drawer.closeDrawers();
        });
        // 清空事件选择
        click(R.id.s_clear, v -> start.clearTime());
        click(R.id.e_clear, v -> end.clearTime());
        click(R.id.c_clear, v -> text(R.id.capacity0, ""));
        click(R.id.n_clear, v -> {
            text(R.id.name, "");
            map(NAME, null);
        });
        click(R.id.reset, v -> {
            get(R.id.s_clear).callOnClick();
            get(R.id.e_clear).callOnClick();
            get(R.id.c_clear).callOnClick();
            map(CAPACITY, null);
            map(START, null);
            map(END, null);
        });

        // 初始化RecyclerView
        adapter = new RoomAdapter(this);

        // 设置下拉刷新事件
        swipe(refreshListener);

        // 设置上拉加载更多
        adapter.setOnLoadMoreListener(loadMoreListener);

        // 设置点击事件
        adapter.setOnItemClickListener((data, position) ->
                new RoomDetailsActivity().start(requireActivity(), data.getId())
        );

        // 初始化加载
        refreshListener.onRefresh();

        // 筛选页面的查询请求
        click(R.id.query, v -> {
            try {
                int anInt = Integer.parseInt(Utils.string(text(R.id.capacity0)));
                map(CAPACITY, anInt);
            } catch (NumberFormatException ignored) {
                map(CAPACITY, null);
            }
            if (start.isSelected() && end.isSelected()) {
                map(START, this.start.getMillisecond());
                map(END, this.end.getMillisecond());
            }
            refreshListener.onRefresh();
            get(R.id.close).callOnClick();
        });

        // 搜索查询请求
        name.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                swipe(true);
                map(NAME, Utils.string(name));
                refreshListener.onRefresh();
                return true;
            }
            return false;
        });
    }

    /**
     * 下拉刷新接口
     */
    private SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
        logger.info("重新加载");
        name.clearFocus();
        closeKeyBord();
        request().success((Net.OnSuccess<RoomPager>) roomPager -> {
            adapter.setData(roomPager.getData());
            adapter.notifyDataSetChanged();
            map(PAGER, roomPager.getPager());
            logger.info("重新加载成功");
        }).end(() -> swipe(false)).run();
    };

    /**
     * 上拉加载更多接口
     */
    private Runnable loadMoreListener = () -> {
        logger.info("加载更多");
        request(map(PAGER)).success((Net.OnSuccess<RoomPager>) roomPager -> {
            if (!roomPager.getData().isEmpty()) {
                adapter.getData().addAll(roomPager.getData());
                adapter.notifyDataSetChanged();
                map(PAGER, roomPager.getPager());
            }
            logger.info("加载更多成功");
        }).run();
    };

    /**
     * 获取封装好数据的请求对象
     *
     * @param pager 当前分页
     */
    private Net.Builder request(@Nullable Pager pager) {
        return Api.roomsPage(pager).param(CAPACITY, map(CAPACITY))
                .param(START, map(START))
                .param(NAME, map(NAME))
                .param(END, map(END));
    }

    private Net.Builder request() {
        return request(null);
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyBord() {
        FragmentActivity activity = requireActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
