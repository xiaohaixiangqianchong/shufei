package com.ubestkid.kidrhymes.constant;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.ubestkid.kidrhymes.utils.ThreadUtils;

import java.io.File;


/**
 * @author cross_ly DATE 2019/04/23
 * <p>描述:常量值
 */
public class Constants {


//
    public static final String  APP_ID = "q1pqwn3lu0u1iz02qkzz587am9euni2j";
    public static final String SLOT_ID_BANNER = "c3ua017weq1c72gpk3pcj33i6dsot7ky";
    public static final String SLOT_ID_SPLASH = "42jmnpwtg4udmugj0gehynqb8h42nbuq";
    public static final String SLOT_ID_NATIVE = "ww0k66dupsbapygdpbfyou99p83lntpq";
    public static final String SLOT_ID_INTERSTITIAL = "pt107eeepqmljt0ep47dbqm0yx2xq54x";

//    public static final String APP_ID = "q1pqwn3lu0u1iz02qkzz587am9euni2j";
//    public static final String SLOT_ID_BANNER = "c3ua017weq1c72gpk3pcj33i6dsot7ky";
//    public static final String SLOT_ID_SPLASH = "42jmnpwtg4udmugj0gehynqb8h42nbuq";
//    public static final String SLOT_ID_NATIVE = "pt107eeepqmljt0ep47dbqm0yx2xq54x";
//    public static final String SLOT_ID_INTERSTITIAL = "ww0k66dupsbapygdpbfyou99p83lntpq";


    /**
     * 本地存储KEY
     */
    public static class SharedAPI {
        public static final String TOKEN_KEY = "token"; //token

    }

    /**
     * 文件存储相关常量
     */
    public static class SDCardConstants {

        private static final String TAG = "SDCardConstants";
        /**
         * 转码文件后缀
         */
        public final static String TRANSCODE_SUFFIX = ".mp4_transcode";

        /**
         * 裁剪文件后缀
         */
        public final static String CROP_SUFFIX = "-crop.mp4";

        /**
         * 合成文件后缀
         */
        public final static String COMPOSE_SUFFIX = "-compose.mp4";

        /**
         * 裁剪 & 录制 & 转码输出文件的目录
         * android Q 版本默认路径
         * /storage/emulated/0/Android/data/包名/files/Media/
         * android Q 以下版本默认"/sdcard/DCIM/Camera/"
         */
        public static String getDir(Context context) {
            String dir;
            if (Build.VERSION.SDK_INT >= 29) {
                dir = context.getExternalFilesDir("") + File.separator + "Media" + File.separator;
            } else {
                dir = Environment.getExternalStorageDirectory() + File.separator + "DCIM"
                        + File.separator + "Camera" + File.separator;
            }
            File file = new File(dir);
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.mkdirs();
            }
            return dir;
        }

        /**
         * 获取外部缓存目录 版本默认"/storage/emulated/0/Android/data/包名/file/Cache/svideo"
         *
         * @param context Context
         * @return string path
         */
        public static String getCacheDir(Context context) {
            File cacheDir = new File(context.getExternalCacheDir(), "svideo");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            return cacheDir.exists() ? cacheDir.getPath() : "";
        }

        /**
         * 清空外部缓存目录文件 "/storage/emulated/0/Android/data/包名/file/Cache/svideo"
         *
         * @param context Context
         */
        public static void clearCacheDir(Context context) {
            final File cacheDir = new File(context.getExternalCacheDir(), "svideo");
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    boolean b = deleteFile(cacheDir);
                    Log.i(TAG, "delete cache file " + b);
                }
            });
        }

        /**
         * 递归删除文件/目录
         *
         * @param file File
         */
        private static boolean deleteFile(File file) {
            if (file == null || !file.exists()) {
                return true;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null) {
                    return true;
                }
                for (File f : files) {
                    deleteFile(f);
                }
            }
            return file.delete();
        }

    }
}
