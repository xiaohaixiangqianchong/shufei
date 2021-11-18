package com.ubestkid.kidrhymes.callback;

import com.hisavana.common.bean.TAdErrorCode;
import com.hisavana.common.bean.TAdNativeInfo;

import java.util.List;

public abstract class STAdCallBack {

    public STAdCallBack() {
    }

    public void onLoad() {
    }

    public void onLoad(List<TAdNativeInfo> var1) {
    }

    public abstract void onError(TAdErrorCode var1);

    public void onStart() {
    }

    public abstract void onShow();

    public abstract void onClicked();

    public abstract void onClosed();

    public void onRewarded() {
    }
}
