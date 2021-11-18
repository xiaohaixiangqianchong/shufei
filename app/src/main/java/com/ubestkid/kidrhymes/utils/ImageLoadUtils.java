package com.ubestkid.kidrhymes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.ubestkid.kidrhymes.AppApplication;

import java.util.concurrent.ExecutionException;

/**
 * Created by SunSt on 2017/3/7.
 * 加载图片工具类
 */
public class ImageLoadUtils {

    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        try {
            Glide.with(context).load(imageUrl).dontAnimate().into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void loadImage(Context context, ImageView imageView, String imageUrl, boolean isUseCache) {
        try {
            if (!isUseCache) {
                Glide.with(context).load(imageUrl)
                        .dontAnimate()
                        .skipMemoryCache(true) // 不使用内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                        .into(imageView);
            } else {
                Glide.with(context).load(imageUrl).dontAnimate().into(imageView);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载as内图片
     *
     * @param context
     * @param imageView
     * @param resource
     */
    public static void loadImage(Context context, ImageView imageView, int resource) {
        try {
            Glide.with(context).load(resource).dontAnimate().into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载本地gif图片,并且去除 加载闪动
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param placeholder
     * @param isGif
     */
    public static void loadImage(Context context, ImageView imageView, int imageUrl, int placeholder, boolean isGif) {
        try {
            if (isGif)
                Glide.with(context).asGif().load(imageUrl).placeholder(placeholder).dontAnimate().into(imageView);
            else
                Glide.with(context).load(imageUrl).placeholder(placeholder).dontAnimate().into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param placeholder
     */
    public static void loadImage(Context context, ImageView imageView, String imageUrl, int placeholder) {
        try {
            if (placeholder == 0) {
                Glide.with(context).load(imageUrl).into(imageView);
            } else {
                Glide.with(context).load(imageUrl).placeholder(placeholder).dontAnimate().into(imageView);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param placeholder
     */
    public static void loadImage(Context context, ImageView imageView, String imageUrl, int placeholder, int errorholder, boolean isCircle) {
        try {
            if (placeholder == 0) {//不需要 placeholder
//            if (isCircle) {
//                Glide.with(context).load(imageUrl).error(errorholder).transform(new GlideCircleTransform(context)).dontAnimate().into(imageView);
//            } else {
                Glide.with(context).load(imageUrl).error(errorholder).dontAnimate().into(imageView);
//            }
            } else {
//            if (isCircle) {
//                Glide.with(context).load(imageUrl).placeholder(placeholder).error(errorholder).transform(new GlideCircleTransform(context)).dontAnimate().into(imageView);
//            } else {
                Glide.with(context).load(imageUrl).placeholder(placeholder).error(errorholder).dontAnimate().into(imageView);
//            }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取网络图片
     *
     * @param photoUrl 图片网络地址
     * @return Bitmap 返回位图
     */
    public static Bitmap getBitmap(String photoUrl) {
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(AppApplication.mContext).asBitmap().load(photoUrl).centerCrop().dontAnimate().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

}