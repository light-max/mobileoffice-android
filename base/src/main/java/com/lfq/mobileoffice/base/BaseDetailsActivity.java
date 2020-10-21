package com.lfq.mobileoffice.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.lfq.mobileoffice.net.Net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 各种详情activity的封装<br>
 * 向 {path}/{id} 链接发送请求<br>
 * 子类必须添加注解：{@link ViewResource},{@link RelativePath}
 *
 * @param <T> 注解实体类
 * @author 李凤强
 */
public abstract class BaseDetailsActivity<T> extends BaseActivity {

    private int targetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        Class<?> aClass = getClass();
        ViewResource viewResource = aClass.getAnnotation(ViewResource.class);
        RelativePath relativePath = aClass.getAnnotation(RelativePath.class);
        setContentView(Objects.requireNonNull(viewResource).value());
        Type type = ((ParameterizedType) Objects.requireNonNull(aClass.getGenericSuperclass())).getActualTypeArguments()[0];
        targetId = getIntent().getIntExtra("targetId", 0);
        Net.builder().method(Net.GET)
                .handler(new Handler())
                .typeOf((Class<?>) type)
                .url("{path}/{targetId}")
                .path("path", Objects.requireNonNull(relativePath).value())
                .path("targetId", targetId)
                .success(this::onLoad)
                .run();
    }

    /**
     * 当请求成功后的回调
     *
     * @param t 具体数据
     */
    protected abstract void onLoad(T t);

    /**
     * 启动详情子类，需要先实例化才能启动
     *
     * @param context
     * @param targetId
     */
    public void start(Context context, int targetId) {
        Intent intent = new Intent(context, getClass());
        intent.putExtra("targetId", targetId);
        context.startActivity(intent);
    }

    /**
     * 获取详情id
     */
    public int getTargetId() {
        return targetId;
    }

    /**
     * 视图资源注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ViewResource {
        int value();
    }

    /**
     * 相对路径注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RelativePath {
        String value();
    }
}
