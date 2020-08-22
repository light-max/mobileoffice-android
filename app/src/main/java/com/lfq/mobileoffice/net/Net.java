package com.lfq.mobileoffice.net;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络请求工具类
 */
public class Net {
    private static Net net = new Net();
    private OkHttpClient client;

    public static final String baseUrl = "http://10.0.2.2:8080";
//    public static final String baseUrl = "http://192.168.0.107:8080";

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    /**
     * 构造函数，初始化OkHttpClient
     */
    private Net() {
        HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        client = new OkHttpClient().newBuilder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                cookieStore.put(httpUrl.host(), list);
            }

            @NotNull
            @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                return cookies != null ? cookies : new ArrayList<>();
            }
        }).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 网络请求构造类
     */
    public static class Builder {
        private String url;
        private String method;
        private RequestBody body;
        private Boolean toast;
        private Handler handler;
        private Data data;

        private Class<?> typeOf;
        private Class<?> listTypeOf;

        private NetCall<?> call;
        private MutableLiveData<?> observe;

        private OnSuccess<?> success;
        private OnFailure failure;
        private OnStart start;
        private OnEnd end;

        /**
         * 执行网络请求
         */
        public void run() {
            // 请求路径
            StringBuilder pathBuilder = new StringBuilder();
            // 如果有路径参数
            if (data != null && data.path != null) {
                // 把路径按“/”分割
                String[] split = url.split("/");
                // 遍历分割好的每一段路径
                for (String s : split) {
                    // 如果这段路径是用大括号包围的就把路径名当作key，从data.path中把路径值取出来拼接
                    if (s.contains("{") && s.contains("}") && s.indexOf("{") < s.indexOf("}")) {
                        String key = s.substring(1, s.length() - 1);
                        Object value = data.path.get(key);
                        // 并且value不为空
                        if (value != null) {
                            pathBuilder.append("/").append(value);
                        }
                    } else {
                        // 按原来的样子拼接
                        pathBuilder.append("/").append(s);
                    }
                }
            } else {
                // 不为空也把{}去掉
                String replaceAll = url.replaceAll("\\{+[a-zA-Z0-9]+\\}", "");
                pathBuilder.append(replaceAll);
            }

            // 如果是GET之外的请求并且没有传入body，就创建一个body
            if (!GET.equals(method) && body == null) {
                // 如果需要传入data中的参数就创建MultipartBody
                if (data != null && data.param != null) {
                    MultipartBody.Builder builder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM);
                    // 遍历data.param，把所有的key和value当做参数和参数名添加到body中
                    data.param.forEach((key, value) -> {
                        builder.addFormDataPart(key, value.toString());
                    });
                    body = builder.build();
                }
                // 创建什么都不带的body
                else {
                    MediaType mediaType = MediaType.parse("text/plain");
                    body = RequestBody.create(mediaType, "");
                }
            }

            // 如果是get请求
            if (GET.equals(method)) {
                // 如果要添加参数就把所有的参数拼接到pathBuilder中
                if (data != null && data.param != null) {
                    pathBuilder.append("?");
                    data.param.forEach((key, value) -> {
                        pathBuilder.append(key).append("=").append(value).append("&");
                    });
                }
            }

            // 构造okhttp的请求对象
            Request request = new Request.Builder()
                    .url(new File(baseUrl, pathBuilder.toString()).getPath())
                    .method(method, body)
                    .build();

            // 在请求前调用call.onStart函数
            if (call != null) {
                call.onStart();
            }
            if (start != null) {
                start.onStart();
            }

            // 创建Callback实现类
            NetCallback callback = new NetCallback()
                    .toast(toast)
                    .handler(handler)
                    .call(call)
                    .observe(observe)
                    .typeOf(typeOf)
                    .typeListOf(listTypeOf)
                    .success(success)
                    .failure(failure)
                    .end(end);

            // 执行请求
            net.client.newCall(request).enqueue(callback);
        }

        /**
         * @param dataType 响应数据转换成的类型
         */
        public Builder typeOf(Class<?> dataType) {
            this.typeOf = dataType;
            return this;
        }

        /**
         * @param dataType 响应数据会转换成List类型，设置List的泛型
         */
        public Builder typeListOf(Class<?> dataType) {
            this.listTypeOf = dataType;
            return this;
        }

        /**
         * 设置请求后的回调
         */
        public <T> Builder call(NetCall<T> call) {
            this.call = call;
            return this;
        }

        /**
         * 设置把成功的数据使用{@link MutableLiveData#setValue(Object)}保存
         */
        public Builder observe(MutableLiveData<?> dataObserve) {
            this.observe = dataObserve;
            return this;
        }

        /**
         * 设置请求url 使用相对路径
         */
        public Builder url(String relativePath) {
            this.url = relativePath;
            return this;
        }

        /**
         * 设置请求方式
         * {@link Net#GET}{@link Net#POST}{@link Net#PUT}{@link Net#DELETE}
         */
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * 设置请求所需的body，不设置会自动生成，设置后{@link Net.Data#addParam(String, Object)}不会生效
         */
        public Builder body(RequestBody body) {
            this.body = body;
            return this;
        }

        /**
         * 设置是否显示错误toast
         */
        public Builder toast(Boolean enable) {
            this.toast = enable;
            return this;
        }

        /**
         * 设置响应|错误的线程handler
         */
        public Builder handler(Handler uiHandler) {
            this.handler = uiHandler;
            return this;
        }

        /**
         * 设置请求数据
         * 如果在{@link #param(String, Object)}或{@link #path(String, Object)}之后调用，则会覆盖原来的数据
         */
        public Builder data(Data data) {
            this.data = data;
            return this;
        }

        /**
         * 功能和{@link Data#addParam(String, Object)}相同
         */
        public Builder param(String key, Object value) {
            if (data == null) {
                data = new Data();
            }
            data.addParam(key, value);
            return this;
        }

        /**
         * 功能和{@link Data#addPath(String, Object)}相同
         */
        public Builder path(String key, Object value) {
            if (data == null) {
                data = new Data();
            }
            data.addPath(key, value);
            return this;
        }

        /**
         * 设置请求成功的回调接口
         */
        public <T> Builder success(OnSuccess<T> success) {
            this.success = success;
            return this;
        }

        /**
         * 设置请求失败后的回调接口
         */
        public Builder failure(OnFailure failure) {
            this.failure = failure;
            return this;
        }

        /**
         * 设置请求开始前的回调接口
         */
        public Builder start(OnStart start) {
            this.start = start;
            return this;
        }

        /**
         * 设置请求结束后的回调
         */
        public Builder end(OnEnd end) {
            this.end = end;
            return this;
        }
    }

    /**
     * 请求所需数据
     */
    public static class Data {
        private Map<String, Object> param;
        private Map<String, Object> path;

        /**
         * 添加请求参数
         */
        public Data addParam(String key, Object value) {
            if (param == null) {
                param = new HashMap<>();
            }
            param.put(key, value);
            return this;
        }

        /**
         * 添加请求路由参数
         * http:10.0.2.2:8080/api/{name} addPath("name","lfq");
         */
        public Data addPath(String key, Object value) {
            if (path == null) {
                path = new HashMap<>();
            }
            path.put(key, value);
            return this;
        }
    }

    /**
     * 请求成功的回调接口
     *
     * @param <T> 返回的数据类型
     */
    public interface OnSuccess<T> {
        /**
         * 当请求成时的回调函数
         *
         * @param t 请求成功后返回的数据
         */
        void onSuccess(T t);
    }

    /**
     * 请求失败的回调接口
     */
    public interface OnFailure {
        /**
         * 当请求失败时的回调函数
         *
         * @param message 失败消息
         */
        void onFailure(String message);
    }

    /**
     * 在请求之前的回调接口
     */
    public interface OnStart {
        /**
         * 这个函数会在请求发起之前调用
         */
        void onStart();
    }

    /**
     * 在请求结束后的回调接口
     */
    public interface OnEnd {
        /**
         * 这个函数会在请求成功或请求失败后调用
         */
        void onEnd();
    }
}
