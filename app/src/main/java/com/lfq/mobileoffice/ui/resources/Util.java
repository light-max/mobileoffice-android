package com.lfq.mobileoffice.ui.resources;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import com.lfq.mobileoffice.data.result.Resource;

import java.io.File;

/**
 * {@link ResourceListActivity}的工具类
 *
 * @author 李凤强
 */
class Util {
    private static File directory;

    /**
     * 获取资源文件对象
     *
     * @param resource   资源
     * @param autoMkdirs 是否自动创建资源文件所在目录
     */
    public static File getFile(Resource resource, boolean autoMkdirs) {
        if (directory == null) {
            directory = Environment.getExternalStoragePublicDirectory("Download/mobileoffice");
        }
        File path = new File(directory, resource.getId());
        if (autoMkdirs) {
            path.mkdirs();
        }
        return new File(path, resource.getName());
    }

    /**
     * 打开文件
     */
    public static void openAndroidFile(Context context, Resource resource) {
        Intent intent = new Intent();
        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        File file = getFile(resource, false);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看
        intent.setDataAndType(Uri.fromFile(file), resource.getType());//设置类型
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "无法打开此文件", Toast.LENGTH_SHORT).show();
        }
    }
}
