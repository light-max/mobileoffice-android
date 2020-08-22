package com.lfq.mobileoffice.net;

import android.os.Handler;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.lfq.mobileoffice.data.result.Result;
import com.lfq.mobileoffice.logger.Logger;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.util.App;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 封装okhttp回调请求
 * 只适用于{@link Result}格式的数据
 */
@LoggerName("Net")
//abstract
class NetCallback implements Callback {
    // 日志工具
    private static final Logger logger = Logger.getLogger(NetCallback.class);

    // 响应结果的类型
    private Type resultType;

    // 响应|错误执行的线程，不设置就是默认线程
    private Handler uiHandler;

    // 是否使用toast显示错误
    private boolean showToast = true;

    // 响应数据监听器
    private MutableLiveData observe;

    // 响应回调
    private NetCall netCall;
    private Net.OnSuccess success;
    private Net.OnFailure failure;
    private Net.OnEnd end;

//    public NetCallback() {
//        Type genericSuperclass = getClass().getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
//            resultType = new ResultType(parameterizedType.getActualTypeArguments());
//            System.out.println("1:"+ Arrays.toString(parameterizedType.getActualTypeArguments()));
//        } else {
//            resultType = new TypeToken<Result<Void>>() {
//            }.getType();
//            System.out.println("2:" + resultType.getTypeName());
//        }
//    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        onFailure("网络异常");
        logger.error("网络请求错误", e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        try {
            Reader charStream = Objects.requireNonNull(response.body()).charStream();
            if (resultType == null) {
                typeOf(Object.class);
            }
            Result<?> result = new Gson().fromJson(charStream, resultType);
            if (result.isSuccess()) {
                if (uiHandler != null) {
                    uiHandler.post(() -> onSuccess(result.getData()));
                } else {
                    onSuccess(result.getData());
                }
                if (observe != null) {
                    observe.postValue(result.getData());
                }
            } else {
                onFailure(result.getMessage());
            }
        } catch (Exception e) {
            onFailure("未知错误");
            logger.error("数据解析错误", e);
        }
    }

    /**
     * 请求失败后的回调
     *
     * @param message 提示消息
     */
    private void onFailure(String message) {
        if (uiHandler != null && showToast) {
            uiHandler.post(() -> {
                Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
            });
        }
        if (netCall != null) {
            netCall.onFailure(message);
            netCall.onEnd();
        }
        if (failure != null) {
            failure.onFailure(message);
        }
        if (end != null) {
            end.onEnd();
        }
    }

    /**
     * 响应成功后的回调，写这个函数是方便为netCall判空
     *
     * @param data 响应数据
     */
    private void onSuccess(Object data) {
        if (netCall != null) {
            netCall.onSuccess(data);
            netCall.onEnd();
        }
        if (success != null) {
            success.onSuccess(data);
        }
        if (end != null) {
            end.onEnd();
        }
    }

    NetCallback typeOf(Class<?> dataType) {
        if (dataType != null) {
            resultType = ResultType.create(dataType);
        }
        return this;
    }

    NetCallback typeListOf(Class<?> dataType) {
        if (dataType != null) {
            resultType = ResultType.list(dataType);
        }
        return this;
    }

    NetCallback handler(@Nullable Handler uiHandler) {
        this.uiHandler = uiHandler;
        return this;
    }

    NetCallback toast(@Nullable Boolean showToast) {
        if (showToast != null) {
            this.showToast = showToast;
        }
        return this;
    }


    NetCallback observe(@Nullable MutableLiveData<?> observe) {
        this.observe = observe;
        return this;
    }

    NetCallback call(@Nullable NetCall<?> call) {
        this.netCall = call;
        return this;
    }

    NetCallback success(@Nullable Net.OnSuccess<?> success) {
        this.success = success;
        return this;
    }

    NetCallback failure(@Nullable Net.OnFailure failure) {
        this.failure = failure;
        return this;
    }

    NetCallback end(@Nullable Net.OnEnd end) {
        this.end = end;
        return this;
    }
}
