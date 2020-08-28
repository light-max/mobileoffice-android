package com.lfq.mobileoffice.ui.home.message.roomapply;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.logger.LoggerName;

/**
 * "我的"会议室预约记录
 */
@LoggerName("会议室预约记录")
public class MeRoomApplyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Override
    public int getViewResource() {
        return R.layout.fragment_room_apply;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        map("pending", new MeRAPendingFragment());
        map("all", new MeRAAllFragment());
        // 切换到待审核列表
        checkChange(R.id.pending, (buttonView, isChecked) -> {
            if (!isChecked) return;
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, map("pending"))
                    .commitNow();
            logger.info("切换: " + map("pending").getClass());
        });
        // 切换到全部列表
        checkChange(R.id.all, (buttonView, isChecked) -> {
            if (!isChecked) return;
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, map("all"))
                    .commitNow();
            logger.info("切换: " + map("all").getClass());
        });
        // 设置待审核列表为默认选中状态
        ((CompoundButton) get(R.id.pending)).setChecked(true);
    }

    @Override
    public void onRefresh() {
        refresh(map("pending"));
        refresh(map("all"));
    }

    private void refresh(SwipeRefreshLayout.OnRefreshListener fragment) {
        if (fragment instanceof Fragment){
            if (((Fragment) fragment).isVisible()) {
                fragment.onRefresh();
            }
        }
    }
}
