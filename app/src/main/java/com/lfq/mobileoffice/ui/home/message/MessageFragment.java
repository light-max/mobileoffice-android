package com.lfq.mobileoffice.ui.home.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.ui.home.message.leave.MeLeave;
import com.lfq.mobileoffice.ui.home.message.notice.NoticeFragment;
import com.lfq.mobileoffice.ui.home.message.reimbursement.MeReimbursement;
import com.lfq.mobileoffice.ui.home.message.roomapply.MeRoomApply;
import com.lfq.mobileoffice.ui.home.message.travel.MeTravel;

/**
 * 消息fragment
 *
 * @author 李凤强
 */
public class MessageFragment extends BaseFragment {
    @Override
    public int getViewResource() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        map(0, new NoticeFragment());
        map(1, new MeRoomApply());
        map(2, new MeLeave());
        map(3, new MeTravel());
        map(4, new MeReimbursement());
        ViewPager2 pager = get(R.id.pager);
        pager.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return map(position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
        new TabLayoutMediator(get(R.id.tab), pager, (tab, position) -> {
            tab.setText(new String[]{
                    "公告",
                    "会议室预约",
                    "我的请假",
                    "我的出差",
                    "我的报销"
            }[position]);
        }).attach();

        swipe(() -> {
            refresh(map(0));
            refresh(map(1));
            refresh(map(2));
            refresh(map(3));
            refresh(map(4));
            swipe(false);
        });
    }

    private void refresh(SwipeRefreshLayout.OnRefreshListener fragment) {
        if (fragment instanceof Fragment) {
            if (((Fragment) fragment).isVisible()) {
                fragment.onRefresh();
            }
        }
    }
}
