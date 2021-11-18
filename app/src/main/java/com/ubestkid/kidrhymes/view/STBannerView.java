package com.ubestkid.kidrhymes.view;

import android.content.Context;
import android.util.AttributeSet;

import com.hisavana.common.bean.TAdRequestBody;
import com.hisavana.mediation.ad.TBannerView;
import com.ubestkid.kidrhymes.presenter.STAdManager;
import com.ubestkid.kidrhymes.utils.STAdUtil;

public class STBannerView extends TBannerView {
    String originalSlotId;

    public STBannerView(Context context) {
        super(context);
    }

    public STBannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public STBannerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    public void setAdUnitId(String s) {
        // TODO: 2021/11/9 id转换
        String news = STAdManager.getInstance().getSlotId(s);
        originalSlotId = s;
        super.setAdUnitId(news);
    }

    @Override
    public void setRequestBody(TAdRequestBody tAdRequestBody) {
        super.setRequestBody(tAdRequestBody);
        STAdUtil.TAdAlliance tAdListener = (STAdUtil.TAdAlliance) tAdRequestBody.getAdListener();
        if (tAdListener != null) {
            tAdListener.onCreate(originalSlotId, true);
        }
    }
}
