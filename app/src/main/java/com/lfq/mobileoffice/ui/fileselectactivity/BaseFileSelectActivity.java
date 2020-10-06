package com.lfq.mobileoffice.ui.fileselectactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import com.lfq.mobileoffice.base.BaseActivity;
import com.lfq.mobileoffice.data.result.Resource;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.fileselect.FileSelectActivity;
import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.util.FileUtil;
import com.lfq.mobileoffice.util.PermissionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件选择基础类<br>
 * 需要子类调用{@link #selectFile()}方法选择文件<br>
 * 调用{@link #picture()}方法选择图片<br>
 * 子类需要拥有{@link androidx.recyclerview.widget.RecyclerView}控件来显示已选择的文件,<br>
 * 并且把{@link androidx.recyclerview.widget.RecyclerView}控件的id设置为{@link com.lfq.mobileoffice.R.id#recycler}.<br>
 * <b>必须重写{@link #getResources()}方法获取视图资源文件的id,否则文件列表适配器初始化时会抛出异常</b>
 */
public abstract class BaseFileSelectActivity extends BaseActivity {

    protected FileListAdapter adapter;
    private boolean disable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewResource());
        adapter = new FileListAdapter(this);
        // 生成已上传的文件
        adapter.setOnDeleteListener((position, file) -> {
            adapter.getData().remove(file);
            adapter.notifyDataSetChanged();
//            Api.fileDelete(map(file)).run();
            map(file, null);
        });
    }

    /**
     * 上传成功后禁用文件选择器
     */
    protected void disableSelected() {
        adapter.setOnDeleteListener(null);
        disable = true;
    }

    /**
     * 获取视图资源id
     */
    protected abstract int getViewResource();

    /**
     * 上传文件
     */
    private void upload(File file) {
        Api.fileUpload(file).end(this::dismissDialog).start(() -> progress("正在上传文件"))
                .success((Net.OnSuccess<Resource>) resource -> {
                    adapter.getData().add(file);
                    adapter.notifyDataSetChanged();
                    map(file, resource);
                }).run();
    }

    private final int FILE = 0x2;
    private final int PICTURE = 0x3;

    /**
     * 选择文件<br><b>供子类调用</b>
     */
    protected void selectFile() {
        if (disable) return;
        if (PermissionUtil.localStorage(this)) {
            Intent intent = new Intent(this, FileSelectActivity.class);
            startActivityForResult(intent, FILE);
        } else {
            toast("请授权文件访问权限");
        }
    }

    /**
     * 选择照片<br><b>供子类调用</b>
     */
    protected void picture() {
        if (disable) return;
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PICTURE);
    }

    /**
     * 获取所有上传的文件的资源id
     */
    protected List<String> getFileResourcesID() {
        List<String> resources = new ArrayList<>();
        for (File datum : adapter.getData()) {
            String resourceId = ((Resource) map(datum)).getId();
            resources.add(resourceId);
        }
        return resources;
    }

    /**
     * 清除所有选中的文件
     */
    protected void clearSelectFile() {
        List<String> ids = getFileResourcesID();
        if (ids.isEmpty()) return;
        Api.filesDelete(ids).run();
    }

    @SuppressLint("Recycle")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        // 选择文件
        if (requestCode == FILE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            assert uri != null;
            File file = new File(Objects.requireNonNull(uri.getPath()));
            upload(file);
        }
        // 选择照片
        else if (requestCode == PICTURE) {
            Uri uri = data.getData();
            assert uri != null;
            String file = FileUtil.getFilePathByUri(this, uri);
            upload(new File(file));
        }
    }
}
