package com.lfq.mobileoffice.ui.home.message;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审核与未审核视图容器，该容器容纳两个fragment，
 * 一个是待审核界面，一个是所有记录界面.<br>
 * 需要添加注解：{@link Pending}与{@link All}
 */
public abstract class PendingReviewAndAll extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RefreshFragment pendingFragment;
    private RefreshFragment allFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pending pending = getClass().getAnnotation(Pending.class);
        All all = getClass().getAnnotation(All.class);
        try {
            if (pending != null && all != null) {
                pendingFragment = pending.value().newInstance();
                allFragment = all.value().newInstance();
            } else {
                RuntimeException exception = new RuntimeException("需要添加注解：" + Pending.class + "与" + All.class);
                logger.error(exception);
                throw exception;
            }
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getViewResource() {
        return R.layout.fragment_pending_review_and_all;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        map("pending", pendingFragment);
        map("all", allFragment);
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
        if (fragment instanceof Fragment) {
            if (((Fragment) fragment).isVisible()) {
                fragment.onRefresh();
            }
        }
    }

    /**
     * 待审核内容界面
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Pending {
        /**
         * @return 待审核内容界面fragment的class
         */
        Class<? extends RefreshFragment> value();
    }

    /**
     * 所有内容界面
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface All {
        /**
         * @return 所有内容界面fragment的class
         */
        Class<? extends RefreshFragment> value();
    }

}
