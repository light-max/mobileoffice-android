package com.lfq.mobileoffice.ui.home.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.ui.notice.NoticeFragment;

/**
 * 消息fragment
 */
public class MessageFragment extends BaseFragment {
    @Override
    public int getViewResource() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        map(0, new NoticeFragment());
        map(1, new NoticeFragment());
        ViewPager2 pager = get(R.id.pager);
        pager.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return map(position);
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });
        new TabLayoutMediator(get(R.id.tab), pager, (tab, position) -> {
            tab.setText(new String[]{
                    "公告",
                    "公告"
            }[position]);
        }).attach();
    }
}
