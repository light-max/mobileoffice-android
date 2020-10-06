package com.lfq.mobileoffice.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.ui.home.contact.ContactFragment;
import com.lfq.mobileoffice.ui.home.me.MeFragment;
import com.lfq.mobileoffice.ui.home.message.MessageFragment;
import com.lfq.mobileoffice.ui.home.room.RoomFragment;

import java.util.Objects;

/**
 * 主页activity
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // 初始化fragment, 全部隐藏
        RoomFragment roomFragment = new RoomFragment();
        MessageFragment messageFragment = new MessageFragment();
        ContactFragment contactFragment = new ContactFragment();
        MeFragment meFragment = new MeFragment();
        // 初始化显示
        replaceFragment(roomFragment);
        //查找控件
        BottomNavigationView nav = get(R.id.nav);

        // 设置属性
        nav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        // 绑定监听事件
        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.room:
                    replaceFragment(roomFragment);
                    break;
                case R.id.message:
                    replaceFragment(messageFragment);
                    break;
//                case R.id.contact:
//                    replaceFragment(contactFragment);
//                    break;
                case R.id.me:
                    replaceFragment(meFragment);
            }
            return true;
        });
    }

    /**
     * 切换fragment
     *
     * @param fragment 要显示的fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment currentFragment = map("currentFragment");
        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment);
        } else {
            transaction.show(fragment);
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.commit();
        map("currentFragment", fragment);
    }
}
