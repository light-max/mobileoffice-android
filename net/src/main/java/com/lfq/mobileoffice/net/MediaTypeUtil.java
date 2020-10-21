package com.lfq.mobileoffice.net;

import android.content.Context;

import com.lfq.mobileoffice.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * mediaType工具
 *
 * @author 李凤强
 */
public class MediaTypeUtil {
    private static final Logger logger = Logger.getLogger("MediaType初始化工具");

    public static void init(Context context) {
        new Thread(() -> Net.mediaType = new HashMap<String, String>() {{
            try {
                InputStream in = context.getResources().openRawResource(R.raw.media_type);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(" ");
                    put(split[0], split[1]);
                }
            } catch (IOException e) {
                logger.error("初始化异常", e);
                throw new RuntimeException(e.getCause());
            }
        }}).start();
    }
}
