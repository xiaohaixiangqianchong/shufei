package com.ubestkid.kidrhymes.utils;

import android.app.Activity;

import com.cloud.hisavana.sdk.common.util.AdLogUtil;
import com.hisavana.common.bean.TAdErrorCode;
import com.hisavana.common.bean.TAdRequestBody;
import com.hisavana.common.constant.ComConstants;
import com.hisavana.common.interfacz.TAdListener;
import com.ubestkid.kidrhymes.callback.STAdCallBack;
import com.ubestkid.kidrhymes.presenter.STAdManager;

import java.lang.ref.WeakReference;

public class STAdUtil {

    STAdCallBack stAdCallBack;

    public TAdRequestBody createRequestBody(Activity activity, STAdCallBack stAdCallBack) {
        this.stAdCallBack = stAdCallBack;
        TAdRequestBody tAdRequest = new TAdRequestBody.AdRequestBodyBuild()
                .setAdListener(new TAdAlliance())
                .build();
        return tAdRequest;
    }


    public class TAdAlliance extends TAdListener {

        String appId;

        String logId;
        boolean isBanner = false;

        public void onCreate(String appId, boolean isBanner) {
            this.appId = appId;
            this.isBanner = isBanner;
            LogUtils.d("TAdAlliance", "appId:" + appId);
        }

        @Override
        public void onLoad() {
            LogUtils.d("TAdAlliance", "onLoad:" + appId);
            STAdManager.getInstance().recordLog("0", appId, "1", new STAdManager.OnRecordCallBack() {
                @Override
                public void onCallBack(String adLogId) {
                    logId = adLogId;
                    if (isBanner) {
                        STAdManager.getInstance().recordLog(logId, appId, "2", null);
                    }
                }
            });
            if (stAdCallBack != null) {
                stAdCallBack.onLoad();
            }
        }

        @Override
        public void onError(TAdErrorCode errorCode) {
            LogUtils.d("TAdAlliance", "onError:" + appId);
            if (stAdCallBack != null) {
                stAdCallBack.onError(errorCode);
            }
        }

        @Override
        public void onStart() {
            LogUtils.d("TAdAlliance", "onStart:" + appId);
            if (stAdCallBack != null) {
                stAdCallBack.onStart();
            }
        }

        @Override
        public void onShow() {
            LogUtils.d("TAdAlliance", "onShow:" + appId);
            if (!isBanner) {
                STAdManager.getInstance().recordLog(logId, appId, "2", null);
            }
            if (stAdCallBack != null) {
                stAdCallBack.onShow();
            }

        }

        @Override
        public void onClicked() {
            STAdManager.getInstance().recordLog(logId, appId, "3", null);

            LogUtils.d("TAdAlliance", "onClicked:" + appId);
            if (stAdCallBack != null) {
                stAdCallBack.onClicked();
            }

        }

        @Override
        public void onClosed() {
            STAdManager.getInstance().recordLog(logId, appId, "4", null);

            LogUtils.d("TAdAlliance", "onClosed:" + appId);
            if (stAdCallBack != null) {
                stAdCallBack.onClosed();
            }

            
        }

    }
}
