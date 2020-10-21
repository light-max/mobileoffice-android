package com.lfq.mobileoffice.net;

import android.os.Handler;

/**
 * 响应设置类
 *
 * @author 李凤强
 */
public class ResponseSetting {

    private static OnAccessDeniedListener accessDeniedListener;
    private static OnShowToastListener showToastListener;

    /**
     * 为响应类设置<b>403</b>回调事件
     */
    public static void accessDenied(OnAccessDeniedListener onAccessDeniedListener) {
        ResponseSetting.accessDeniedListener = onAccessDeniedListener;
    }

    /**
     * 为响应设置接口，实现toast回调事件
     * <b>此接口可以在{@link Net.Builder#handler(Handler)}的线程中运行</b>
     */
    public static void showToast(OnShowToastListener onShowToastListener) {
        ResponseSetting.showToastListener = onShowToastListener;
    }

    public static OnAccessDeniedListener accessDenied() {
        return accessDeniedListener;
    }

    public static OnShowToastListener showToast() {
        return showToastListener;
    }

    /**
     * <b>403</b>
     * 当服务端拒绝访问时的回调接口
     */
    public interface OnAccessDeniedListener {
        void onAccessDenied();
    }

    /**
     * <b>400</b>
     * 当{@link NetCallback#onFailure(String)}需要使用toast弹出错误消息的回调接口
     */
    public interface OnShowToastListener {
        void onToast(String message);
    }
}
