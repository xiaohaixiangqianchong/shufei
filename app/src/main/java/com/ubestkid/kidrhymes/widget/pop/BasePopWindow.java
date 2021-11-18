package com.ubestkid.kidrhymes.widget.pop;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.ubestkid.kidrhymes.R;
import com.ubestkid.kidrhymes.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Des
 * @Date 2020/3/7
 * @Author huqinghan
 */
public abstract class BasePopWindow extends PopupWindow {
    public Activity mContext;

    public View view;

    public BasePopWindow(Activity context) {
        super(LayoutInflater.from(context).inflate(R.layout.base_pop_window_layout, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.mContext = context;
        view = setContentView(context);
        ((LinearLayout) getContentView().findViewById(R.id.layout)).addView(view);
        ButterKnife.bind(this, getContentView());
        setFocusable(true);
        initView();
        getContentView().findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void initView() {

    }

    protected abstract View setContentView(Activity context);

    public void show() {
        try {
            showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } catch (Exception e) {
        }
        ObjectAnimator
                .ofFloat(view, "translationY", UiUtils.dip2px(150), 0)
                .setDuration(200).start();
        ObjectAnimator
                .ofFloat(view, "alpha", 0f, 1f)
                .setDuration(200).start();
    }


    public void showCenter() {
        try {
            showAtLocation(mContext.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        } catch (Exception e) {
        }
        ObjectAnimator
                .ofFloat(view, "translationY", UiUtils.dip2px(150), 0)
                .setDuration(200).start();
        ObjectAnimator
                .ofFloat(view, "alpha", 0f, 1f)
                .setDuration(200).start();
    }

    @Override
    public void dismiss() {
//        Ioc.get(EventBus.class).unregisterListener(API.Event.payVerify, getClass().getSimpleName());
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(view, "translationY", 0, UiUtils.dip2px(150))
                .setDuration(200);
        ObjectAnimator
                .ofFloat(view, "alpha", 1f, 0f)
                .setDuration(200).start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                superDismiss();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
        anim.start();
    }

    private void superDismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

//    @OnClick(R.id.layout)
    public void onViewClicked() {
        dismiss();
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}