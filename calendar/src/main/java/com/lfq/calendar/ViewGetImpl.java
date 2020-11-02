package com.lfq.calendar;

import android.view.View;

import com.lfq.mobileoffice.base.interfaces.ViewGet;

/**
 * @author 李凤强
 */
public interface ViewGetImpl extends ViewGet {
    @Override
    default <T extends View> T get(int viewId) {
        if (this instanceof View) {
            return ((View) this).findViewById(viewId);
        } else {
            return null;
        }
    }
}
