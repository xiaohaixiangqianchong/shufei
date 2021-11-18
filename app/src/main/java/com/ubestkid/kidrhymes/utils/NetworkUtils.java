package com.ubestkid.kidrhymes.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ubestkid.kidrhymes.common.ioc.IocContainer;


public class NetworkUtils {
    public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";
    public static final String WIFI = "Wi-Fi";
    public static final String TWO_OR_THREE_G = "2G/3G";
    public static final String UNKNOWN = "Unknown";

    private static String convertIntToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "."
                + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
    }


    /**
     * 网络可用
     * android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     * @param
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) IocContainer.getShare().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static boolean isGpsAvailable(Context ctx) {

        return false;
    }



}