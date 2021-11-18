package com.ubestkid.kidrhymes.utils;

import android.util.Log;

/**
 * 日志输出 管理
 *
 * @author SunSt
 */
public class LogUtils {
    public static final boolean LOG_ENABLE = true;

    public static void i(String tag, String message) {
        if (LOG_ENABLE)
            Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        if (LOG_ENABLE)
            Log.e(tag, message);

    }

    public static void d(String tag, String message) {
        if (LOG_ENABLE)
            Log.d(tag, message);
    }

    public static void v(String tag, String message) {
        if (LOG_ENABLE)
            Log.v(tag, message);
    }



    public static void longLog(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }

}
