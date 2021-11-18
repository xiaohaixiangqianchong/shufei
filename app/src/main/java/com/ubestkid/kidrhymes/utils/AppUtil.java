package com.ubestkid.kidrhymes.utils;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ubestkid.kidrhymes.common.ioc.IocContainer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {


    static String deviceId;
    static String version;
    static String versionName;

    public static final String sinaCls = "com.sina.weibo.SplashActivity";

    /***
     * 获取top的Activity的ComponentName
     *
     * @param paramContext
     * @return
     */
    public static ComponentName getTopActivityComponentName(Context paramContext) {
        List<ActivityManager.RunningTaskInfo> localList = null;
        if (paramContext != null) {
            ActivityManager localActivityManager = (ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (localActivityManager != null) {
                localList = localActivityManager.getRunningTasks(1);

                if ((localList == null) || (localList.size() <= 0)) {
                    return null;
                }
            }
        }
        ComponentName localComponentName = localList.get(0).topActivity;
        return localComponentName;
    }

    /***
     * 查看是否后台
     *
     * @param paramContext
     * @return
     */
    public static boolean isAppRunningBackground(Context paramContext) {
        String pkgName = null;
        List<RunningAppProcessInfo> localList = null;
        if (paramContext != null) {
            pkgName = paramContext.getPackageName();
            ActivityManager localActivityManager = (ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (localActivityManager != null) {
                localList = localActivityManager.getRunningAppProcesses();
                if ((localList == null) || (localList.size() <= 0)) {
                    return false;
                }
            }
        }

        for (Iterator<RunningAppProcessInfo> localIterator = localList.iterator(); localIterator.hasNext(); ) {
            RunningAppProcessInfo info = localIterator.next();
            if (info.processName.equals(pkgName) && info.importance != 100) {
                return true;
            }
        }
        return false;
    }

    public static String getVersion() {
        if (TextUtils.isEmpty(version)) {
            PackageManager packageManager = IocContainer.getShare()
                    .getApplicationContext().getPackageManager();
            PackageInfo packInfo = null;
            try {
                packInfo = packageManager
                        .getPackageInfo(IocContainer.getShare()
                                .getApplicationContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            version = packInfo.versionCode + "";
        }
        return version;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(versionName)) {
            PackageManager packageManager = IocContainer.getShare()
                    .getApplicationContext().getPackageManager();
            PackageInfo packInfo = null;
            try {
                packInfo = packageManager
                        .getPackageInfo(IocContainer.getShare()
                                .getApplicationContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            versionName = packInfo.packageName + "";
        }
        return versionName;
    }

    public static boolean isAppInstall(String packageName) {
        PackageManager pm = IocContainer.getShare().get(PackageManager.class);
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    public static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.setDescription("default");
            IocContainer.getShare().get(NotificationManager.class).createNotificationChannel(mNotificationChannel);
        } else {

        }
    }

    public static Notification notify(Context context, int ic, String title, String msg, Class clazz, Bundle extras) {
        try {

            Intent intent = new Intent(context, clazz);
            if (extras != null) {
                intent.putExtras(extras);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 10086,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Builder builder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new NotificationCompat.Builder(context, "default");
            } else {
                builder = new NotificationCompat.Builder(context, null);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }
            builder.setContentTitle(title);
            builder.setContentText(msg);
            builder.setSmallIcon(ic);
            builder.setContentIntent(contentIntent);
            builder.setTicker(msg);
            builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    public static Notification notify(Context context, Bitmap bitmap, int ic, String title, String msg, Class clazz, Bundle extras) {
        try {

            Intent intent = new Intent(context, clazz);
            if (extras != null) {
                intent.putExtras(extras);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 10086,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Builder builder = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder = new NotificationCompat.Builder(context, "default");
            } else {
                builder = new NotificationCompat.Builder(context, null);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }
            builder.setContentTitle(title);
            builder.setContentText(msg);
            builder.setSmallIcon(ic);
            builder.setLargeIcon(bitmap);
            builder.setContentIntent(contentIntent);
            builder.setTicker(msg);
            builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 打开其他应用
     *
     * @param pkg
     * @param cls
     * @param context
     */
    public static void openStartActivity(String pkg, String cls, Context context) {
        try {
            if (uninstallSoftware(context, pkg)) {
                ComponentName componet = new ComponentName(pkg, cls);
                Intent intent = new Intent();
                intent.setComponent(componet);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "应用不存在", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "应用不存在", Toast.LENGTH_LONG).show();
        }
    }

    //判断应用是否已安装
    public static boolean uninstallSoftware(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            @SuppressLint("WrongConstant") PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[videolist_menu_out-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    /**
     * 版本号比较
     * 版本号格式:1.1.1
     *
     * @param version1 服务器版本号
     * @param version2 本地版本号
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        LogUtils.d("版本号比较-", "version1Array==" + version1Array.length);
        LogUtils.d("版本号比较", "version2Array==" + version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        LogUtils.d("版本号比较", "循环判断每位的大小=" + version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    public static String getKey(String  content) {

        String md5 = md5(content);
        return Base64Encoder.encode(md5.getBytes());
    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
