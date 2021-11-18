package com.ubestkid.kidrhymes.view;

import android.content.Context;

import com.hisavana.common.bean.TAdRequestBody;
import com.hisavana.mediation.ad.TNativeAd;
import com.ubestkid.kidrhymes.presenter.STAdManager;
import com.ubestkid.kidrhymes.utils.STAdUtil;

public class STNativeAd extends TNativeAd {

    String originalSlotId;

    public STNativeAd(Context context, String s) {
        super(context, s);
        this.originalSlotId = s;
        this.j = STAdManager.getInstance().getSlotId(s);
        // TODO: 2021/11/9 转换 
    }

    @Override
    public void setRequestBody(TAdRequestBody tAdRequestBody) {
        super.setRequestBody(tAdRequestBody);
        STAdUtil.TAdAlliance tAdListener = (STAdUtil.TAdAlliance) tAdRequestBody.getAdListener();
        if (tAdListener != null) {
            tAdListener.onCreate(originalSlotId,false);
        }
    }
}
