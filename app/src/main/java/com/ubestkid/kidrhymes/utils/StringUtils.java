package com.ubestkid.kidrhymes.utils;

import java.text.DecimalFormat;

public class StringUtils {

    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;
    }

    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F)) + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F)) + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F)) + "GB" : "error")));
    }

    /**
     * 正数或负数
     *
     * @param b
     * @return
     */
    public static String getRMB(boolean b) {
        if (b) {
            return " ¥";
        } else {
            return "-¥";
        }
    }


    /**
     * 正数或负数
     */
    public static String getRMB() {
        return "¥";
    }
}
