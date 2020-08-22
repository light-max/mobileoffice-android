package com.lfq.mobileoffice.net;

public interface NetCall<T> {
    /**
     * 请求成功的回调
     *
     * @param t 响应数据
     */
    default void onSuccess(T t) {
    }

    /**
     * 请求失败的回调
     *
     * @param message 失败消息
     */
    default void onFailure(String message) {
    }

    /**
     * 请求结束后的回调
     * 在子类{@link #onFailure(String)}回调之前调用
     * 在子类{@link #onSuccess(T)}回调之前调用
     */
    default void onEnd() {
    }

    /**
     * 在请求前调用
     */
    default void onStart() {
    }
}
