package com.lfq.mobileoffice.ui.resources;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.result.Resource;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.util.PermissionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 资源文件列表activity
 *
 * @author 李凤强
 */
public class ResourceListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useBackButton();
        setContentView(R.layout.activity_resource_list);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url == null) {
            RuntimeException exception = new RuntimeException("没有找到url对象");
            logger.error(exception);
            throw exception;
        }
        if (!url.contains("{targetId}")) {
            RuntimeException exception = new RuntimeException("url对象中没有发现{targetId}路径");
            logger.error(exception);
            throw exception;
        }
        long targetId = intent.getLongExtra("targetId", 0);
        if (targetId <= 0) {
            RuntimeException exception = new RuntimeException("请设置大于0的targetId");
            logger.error(exception);
            throw exception;
        }

        ResourceListAdapter adapter = new ResourceListAdapter(this);

        Net.builder().method(Net.GET).url(url).path("targetId", targetId)
                .typeListOf(Resource.class)
                .handler(new Handler())
                .success((Net.OnSuccess<List<Resource>>) resources -> {
                    if (!resources.isEmpty()) {
                        adapter.setData(resources);
                        adapter.notifyDataSetChanged();
                    }
                    adapter.getView().setVisibility(resources.isEmpty() ? View.GONE : View.VISIBLE);
                    get(R.id.empty).setVisibility(resources.isEmpty() ? View.VISIBLE : View.GONE);
                }).run();

        // 下载
        adapter.setOnDownloadListener((downloadButton, resource) -> {
            if (PermissionUtil.localStorage(this)) {
                download(downloadButton, resource);
            } else {
                toast("没有文件读写权限");
            }
        });

        // 删除
        adapter.setOnDeleteListener((deleteButton, resource) -> {
            new AlertDialog.Builder(this)
                    .setMessage("你确定要删除在本地缓存的文件（不会从服务器中删除）")
                    .setPositiveButton("确定", (dialog, which) -> {
                        Util.getFile(resource, false).delete();
                        deleteButton.setVisibility(View.GONE);
                    }).setNegativeButton("取消", null)
                    .show();
        });

        // 打开文件
        adapter.setOnItemClickListener((data, position) -> {
            Util.openAndroidFile(this, data);
        });
    }

    private void download(TextView deleteButton, Resource resource) {
        File file = Util.getFile(resource, true);
        if (file.exists()) {
            toast("文件已存在");
        } else {
            progress("正在下载");
            new Thread(() -> {
                try {
                    Request request = new Request.Builder()
                            .url(Api.url("resource/" + resource.getId()))
                            .method("GET", null)
                            .build();
                    Response response = Net.net.client.newCall(request).execute();
                    InputStream in = Objects.requireNonNull(response.body()).byteStream();
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] bytes = new byte[1024 * 1024 * 10];
                    int len;
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    out.close();
                    runOnUiThread(() -> {
                        dismissDialog();
                        toast("下载完成");
                        deleteButton.setVisibility(View.VISIBLE);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        toast("发生了错误: " + e.getMessage());
                        file.delete();
                    });
                }
            }).start();
        }
    }

    /**
     * 启动方法
     *
     * @param context  上下文
     * @param url      资源url,资源路径中必须包含{targetId}
     * @param targetId 目标id
     */
    public static void start(Context context, String url, long targetId) {
        Intent intent = new Intent(context, ResourceListActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("targetId", targetId);
        context.startActivity(intent);
    }
}
