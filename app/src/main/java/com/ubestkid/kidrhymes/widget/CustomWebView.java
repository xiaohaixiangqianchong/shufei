package com.ubestkid.kidrhymes.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ubestkid.kidrhymes.R;


/**
 * Created by SunSt on 2017/6/15.
 * <p>
 * 带进度条的webview
 */

public class CustomWebView extends WebView {

    private ProgressBar progressbar;

    public CustomWebView(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 5));
        ClipDrawable d = new ClipDrawable(new ColorDrawable(getResources().getColor(R.color.blue)), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progressbar.setProgressDrawable(d);
        addView(progressbar);
        initWebView();
        //结合使用
        setWebChromeClient(new CustomWebChromeClient());
        setWebViewClient(new WebViewClient());
    }

    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return context.createConfigurationContext(new Configuration());
            }
        }
        return context;
    }

    public void initWebView() {
        //启用支持JavaScript
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(true);
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
    }

    public class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE) {
                    progressbar.setVisibility(VISIBLE);
                }
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    /**
     * progressbar 是否显示
     *
     * @param isVisibility
     */
    public void progressbarIsVisibility(boolean isVisibility) {
        if (progressbar == null) {
            return;
        }

        if (isVisibility) {
            progressbar.setVisibility(VISIBLE);
        } else {
            progressbar.setVisibility(GONE);
        }
    }

    /**
     * progressbar 获取
     *
     */
    public ProgressBar getProgressbar() {
        return progressbar == null ? null : progressbar;
    }

}