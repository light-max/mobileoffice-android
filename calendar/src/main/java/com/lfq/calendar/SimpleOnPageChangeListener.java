package com.lfq.calendar;

import androidx.viewpager.widget.ViewPager;

/**
 * 简化{@link ViewPager.OnPageChangeListener}，不需要的方法可以不用实现
 */
public interface SimpleOnPageChangeListener extends ViewPager.OnPageChangeListener {
    @Override
    default void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
    };

    @Override
    default void onPageScrollStateChanged(int state){
    };

    @Override
    void onPageSelected(int position);
}
