package com.ubestkid.kidrhymes.presenter;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.hisavana.mediation.config.TAdManager;
import com.lzy.okgo.OkGo;
import com.ubestkid.kidrhymes.AppApplication;
import com.ubestkid.kidrhymes.bean.LoginBean;
import com.ubestkid.kidrhymes.constant.API;
import com.ubestkid.kidrhymes.constant.Constants;
import com.ubestkid.kidrhymes.net.DResponse;
import com.ubestkid.kidrhymes.net.NormalCallBack;
import com.ubestkid.kidrhymes.utils.AppUtil;
import com.ubestkid.kidrhymes.utils.JSONUtil;
import com.ubestkid.kidrhymes.utils.LogUtils;
import com.ubestkid.kidrhymes.utils.SafeJsonUtil;
import com.ubestkid.kidrhymes.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class STAdManager {
    static STAdManager instance;

    String originalAppId;

    OnRecordCallBack onRecordCallBack;

    public static STAdManager getInstance() {
        if (instance == null) {
            instance = new STAdManager();
        }

        return instance;
    }


    public void initSdk(String appId, Context context,boolean isDebug,boolean isTestDevice) {
        this.originalAppId = appId;
        String versionName = AppUtil.getVersionName();
        long time = System.currentTimeMillis();
        String key = AppUtil.getKey(appId + versionName + time);
        OkGo.<String>get(API.getAppInfo).tag(this)
                .params("key", key)
                .params("app_id", appId)
                .params("time", time)

                .execute(new NormalCallBack(context) {
                    @Override
                    public void onSuccessUI(DResponse response, Call call) {
                        if (response.isSuccess()) {
                            JSONObject dataJo = response.getData();
                            String hisavana_appid = JSONUtil.getString(dataJo, "hisavana_appid");
                            JSONArray adJsa = JSONUtil.getJSONArray(dataJo, "adList");
                            if (adJsa == null || adJsa.length() == 0) {
                                return;
                            }
                            SharedPreferencesUtils.put(context, "adData", adJsa.toString());
                            TAdManager.init(context, new TAdManager.AdConfigBuilder()
                                    .setAppId(hisavana_appid)
                                    .setDebug(isDebug)
                                    .testDevice(isTestDevice)
                                    .build());
                        }
                    }
                });

    }

    public String getSlotId(String key) {
        String adJsaStr = (String) SharedPreferencesUtils.get(AppApplication.mContext, "adData", "");
        if (TextUtils.isEmpty(adJsaStr)) {
            LogUtils.d("shufei", "平台未配置对应的广告");
            return "";
        }
        try {
            JSONArray adJsa = new JSONArray(adJsaStr);
            for (int i = 0; i < adJsa.length(); i++) {
                JSONObject itemJo = JSONUtil.getJSONObjectAt(adJsa, i);
                if (itemJo.has(key)) {
                    return JSONUtil.getString(itemJo, key);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.d("shufei", "该广告位不存在");
        return "";
    }


    public void recordLog(String ad_log_id, String ad_space_id, String status, OnRecordCallBack onRecordCallBack) {
        String versionName = AppUtil.getVersionName();
        long time = System.currentTimeMillis();
        String key = AppUtil.getKey(originalAppId + versionName + time + ad_space_id + ad_log_id + status);
        OkGo.<String>post(API.saveRecord).tag(this)
                .params("ad_log_id", ad_log_id)
                .params("ad_space_id", ad_space_id)
                .params("status", status)
                .params("key", key)
                .params("app_id", originalAppId)
                .params("time", time)

                .execute(new NormalCallBack(AppApplication.mContext) {
                    @Override
                    public void onSuccessUI(DResponse response, Call call) {
                        if (response.isSuccess()) {
                            JSONObject jo = response.getData();

                            String ad_log_id = JSONUtil.getString(jo, "ad_log_id");
                            if (onRecordCallBack != null) {
                                onRecordCallBack.onCallBack(ad_log_id);
                            }

                        }
                    }
                });
    }

    public interface OnRecordCallBack {
        void onCallBack(String adLogId);
    }


}
