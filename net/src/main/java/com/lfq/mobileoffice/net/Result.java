package com.lfq.mobileoffice.net;

import org.jetbrains.annotations.NotNull;

/**
 * 请求返回结果
 *
 * @param <T>
 * @author 李凤强
 */
public class Result<T> {

    /**
     * status : 200
     * message : 操作成功
     * success : true
     */

    private int status;
    private String message;
    private boolean success;
    private T data;

    /**
     * 是否拒绝访问
     *
     * @return true: 是
     */
    public boolean isAccessDenied() {
        return status == 403;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NotNull
    @Override
    public String toString() {
        return "Resp{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
