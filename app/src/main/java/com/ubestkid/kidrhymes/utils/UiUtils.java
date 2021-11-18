package com.ubestkid.kidrhymes.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.ubestkid.kidrhymes.AppApplication;


/**
 * @author: SunsT
 * @date: 2018/11/2 13:11
 * @desciption: 界面 相关工具类
 */
public class UiUtils {

    private UiUtils() {
    }

    public static UiUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * dip转px
     *
     * @param dip
     * @return
     */
    public static int dip2px(float dip) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return AppApplication.mContext;
    }

    /**
     * 屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * px转dip
     *
     * @param px
     * @return
     */
    public static int px2dip(int px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取字符数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取颜色id
     */
    public static int getColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(colorId, null);
        } else {
            return getResources().getColor(colorId);
        }
    }

    public static Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(id, null);
        } else {
            return getResources().getDrawable(id);
        }
    }

    /**
     * 根据id获取尺寸
     */
    public static int getDimens(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    public static String getString(int resId) {
        return getContext().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    public static int getTextSize(int id) {
        TypedValue value = new TypedValue();
        getResources().getValue(id, value, true);
        return (int) TypedValue.complexToFloat(value.data);
    }

    /**
     * 状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private static final class Holder {
        private static final UiUtils INSTANCE = new UiUtils();
    }

//    /**
//     * 保持tab下划线宽度和文字一致
//     */
//    public static void setIndicatorSame(@NonNull final TabLayout tabLayout) {
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Field field = tabLayout.getClass().getDeclaredField("mTabStrip");
//                    field.setAccessible(true);
//                    //拿到tabLayout的mTabStrip属性
//                    LinearLayout tabStrip = (LinearLayout) field.get(tabLayout);
//                    for (int i = 0, count = tabStrip.getChildCount(); i < count; i++) {
//                        View tabView = tabStrip.getChildAt(i);
//                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//                        TextView textView = (TextView) mTextViewField.get(tabView);
//                        tabView.setPadding(0, 0, 0, 0);
//                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int textWidth = 0;
//                        textWidth = textView.getWidth();
//                        if (textWidth == 0) {
//                            textView.measure(0, 0);
//                            textWidth = textView.getMeasuredWidth();
//                        }
//                        int tabWidth = 0;
//                        tabWidth = tabView.getWidth();
//                        if (tabWidth == 0) {
//                            tabView.measure(0, 0);
//                            tabWidth = tabView.getMeasuredWidth();
//                        }
//                        LinearLayout.LayoutParams tabViewParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        int margin = (tabWidth - textWidth) / 2;
//                        //LogUtils.d("textWidth=" + textWidth + ", tabWidth=" + tabWidth + ", margin=" + margin);
//                        tabViewParams.leftMargin = margin;
//                        tabViewParams.rightMargin = margin;
//                        tabView.setLayoutParams(tabViewParams);
//                    }
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


    /**
     * 设置屏幕透明度
     *
     * @param activity
     * @param f
     */
    public static void setScreenAlpha(Activity activity, float f) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = f;
        if (f == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,可能出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

    /**
     * @description 复制文本至剪切板
     * @param
     * @author huqinghan
     * @time 2019/12/15
     */
    public static void copyText(String text) {
        if (text == null) return;
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}