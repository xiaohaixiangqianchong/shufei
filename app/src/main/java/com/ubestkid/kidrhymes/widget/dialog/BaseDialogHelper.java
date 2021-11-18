package com.ubestkid.kidrhymes.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.ubestkid.kidrhymes.R;

/**
 * @Title：弹框基类
 * @Description：
 * @Author：huqinghan(19044311) huqinghan
 * @Date：2019/7/23
 */
public abstract class BaseDialogHelper {
    private static final String TAG = "BaseDialogHelper";
    /**
     * 上下文
     */
    public Activity mThat;

    /**
     * 弹窗 显示动画
     */
    private Animation mShowAnim;

    /**
     * 弹窗 显示动画id
     */
    private int mShowAnimId;

    /**
     * 弹窗 关闭动画
     */
    private Animation mCloseAnim;

    /**
     * 弹窗 关闭动画id
     */
    private int mCloseAnimId;

    /**
     * 弹窗布局
     */
    private View mContentView;

    /**
     * 列表弹窗
     */
    protected AlertDialog mDialogWindow;

    /**
     * 是否启用弹窗动画 默认启用
     */
    private boolean mAnimEnable = true;

    /**
     * 是否第一次加载
     */
    private boolean isFrist = true;

    /**
     * 背景关闭弹窗开关 背景LayoutId为out_area生效
     */
    private boolean mBackgroundCloseEnable = true;

    /**
     * 弹框背景颜色
     */
    private Drawable mBackgroundDrawable;

    /**
     * 是否正在关闭弹框
     */
    private boolean isClosing = false;

    /**
     * 内置加载视图
     */
    private View mLoadView;

    public BaseDialogHelper(Activity that){
        this.mThat = that;
        mContentView = LayoutInflater.from(mThat).inflate(getLayoutId(), null);
        //显示列表弹窗 mDialogWindow
        mDialogWindow = new AlertDialog.Builder(mThat).create();
        initAnim();
        initView(mContentView);
    }

    /**
     * @description 设置弹框布局
     * @param
     * @author 19044311
     * @time 2019/7/23
     */
    public abstract int getLayoutId();

    /**
     * @description 设置弹框布局
     * @param
     * @author 19044311
     * @time 2019/7/23
     */
    public abstract void initView(View view);

    /**
     * @description 第一次加载
     * @param
     * @author 19044311
     * @time 2019/7/24
     */
    private void loadFirst(){
        initDialog();
    }

    /**
     * @description 初始化弹框属性
     * @param
     * @author 19044311
     * @time 2019/7/24
     */
    private void initDialog(){
        mDialogWindow.setContentView(mContentView);
        //设置window全屏，默认的背景会有Padding值，不能全屏。
        mDialogWindow.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        //背景透明度
        mDialogWindow.getWindow().setBackgroundDrawable(mBackgroundDrawable == null ? new ColorDrawable(Color.parseColor("#99000000")) : mBackgroundDrawable);
        //dialog布局全屏
        mDialogWindow.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //去掉dialog本身的灰色透明背景
        mDialogWindow.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //弹窗布局可以使用输入法
        mDialogWindow.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /**
     * @description 设置背景颜色
     * @param drawable 颜色
     * @author 19044311
     * @time 2019/7/23
     */
    public void setBackground(Drawable drawable){
        this.mBackgroundDrawable = drawable;
    }

    /**
     * @description 设置动画
     * @param startId 开始动画
     * @param closeId 结束动画
     * @author 19044311
     * @time 2019/7/23
     */
    public void setAnim(int startId,int closeId){
        this.mShowAnimId = startId;
        this.mCloseAnimId = closeId;
        initAnim();
    }

    /**
     * @description 设置默认动画
     * @param
     * @author 19044311
     * @time 2019/7/23
     */
    private void initAnim(){
        if (mAnimEnable){
            //无设置 则启用默认动画
            initAnim(this.mShowAnimId == 0 ? R.anim.order_list_bottom_in : this.mShowAnimId,
                    this.mCloseAnimId == 0 ? R.anim.order_list_bottom_out : this.mCloseAnimId);
        }
    }

    /**
     * @description 设置动画
     * @param
     * @author 19044311
     * @time 2019/7/24
     */
    private void initAnim(int startId,int closeId){
        //增加 dialog 由下往上 弹出动画
        mShowAnim = AnimationUtils.loadAnimation(mThat, startId);

        //增加 dialog 自上往下 关闭动画
        mCloseAnim = AnimationUtils.loadAnimation(mThat, closeId);
        mCloseAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isClosing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isClosing = false;
                if (mContentView != null && mDialogWindow != null){
                    //用view的post()方法不会报错，直接用super.dismiss()会报错的，你可以试试。
                    mContentView.post(new Runnable() {
                        @Override
                        public void run() {
                            //关闭弹窗
                            mDialogWindow.cancel();
                            //清除动画
                            mContentView.clearAnimation();
                        }
                    });
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * @description 打开弹窗
     * @param
     * @author 19044311
     * @time 2019/7/23
     */
    public void showDialog(){
        //add by huqinghan/19044311 2019/07/01
        if (mThat == null || mThat.isFinishing()) {
            return;
        }

        //弹窗未打开,启用打开动画
        if (mDialogWindow != null && mContentView != null && !mDialogWindow.isShowing()) {
            //必须先执行mDialogWindow.show(); 在设置setContentView
            //启动动画 并且动画不为空
            mDialogWindow.show();
            if (mAnimEnable && mShowAnim != null){
                mContentView.startAnimation(mShowAnim);
            }
            //第一次弹窗需设置setContentView
            if (isFrist){
                isFrist = !isFrist;
                loadFirst();
            }

        }
    }

    /**
     * @description 关闭弹窗
     * @param
     * @author 19044311
     * @time 2019/7/23
     */
    public void closeDialog(){
        //关闭动画执行时 不予处理 防止多次点击
        if (isClosing){
            return;
        }
        if (mDialogWindow != null && mContentView != null){
            //启动动画 并且动画不为空
            if (mAnimEnable && mCloseAnim != null){
                mContentView.startAnimation(mCloseAnim);
            }else {
                //关闭弹窗
                mDialogWindow.cancel();
            }
        }
    }

    /**
     * @description 是否启用动画
     * @param animEnable 开关
     * @author 19044311
     * @time 2019/7/23
     */
    public void setAnimEnable(boolean animEnable){
        this.mAnimEnable = animEnable;
    }


    /**
     * @description 背景关闭开关 背景LayoutId为out_area生效
     * @param backgroundCloseEnable 开关， 默认开
     * @author 19044311
     * @time 2019/7/25
     */
    public void setBackgroundCloseEnable(boolean backgroundCloseEnable){
        this.mBackgroundCloseEnable = backgroundCloseEnable;
    }

    /**
     * 弹框样式toast
     */
    public void showToast(String msg){
        ToastUtils.showShortToast(mThat,msg);
    }
}