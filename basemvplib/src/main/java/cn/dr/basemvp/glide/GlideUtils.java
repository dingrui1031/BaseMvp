package cn.dr.basemvp.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import cn.dr.basemvp.utils.ConverterUtils;

/**
 * @desc 图片加载工具类
 */
public class GlideUtils {

    /**
     * 加载图片
     *
     * @param context  context
     * @param iv       imageView
     * @param url      图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadImage(Context context, ImageView iv, String url, int emptyImg) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(iv);
        } else {
            loadImage(context, iv, emptyImg, emptyImg);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context  context
     * @param iv       imageView
     * @param url      图片地址
     * @param emptyImg 默认展位图
     * @param radius   圆角
     */
    public static void loadRoundImage(Context context, ImageView iv, String url, int emptyImg, int radius) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .transform(new RoundedCorners(radius)).into(iv);
        } else {
            loadRoundImage(context, iv, emptyImg, emptyImg, radius);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context  context
     * @param iv       imageView
     * @param url      图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadCircleImage(Context context, ImageView iv, String url, int emptyImg) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(context)
                    .load(url)
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .transform(new CircleCrop()).into(iv);
        } else {
            loadCircleImage(context, iv, emptyImg, emptyImg);
        }
    }

    /**
     * 加载drawable中的图片资源
     *
     * @param context  context
     * @param iv       imageView
     * @param resId    图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadImage(Context context, ImageView iv, int resId, int emptyImg) {
        GlideApp.with(context).load(resId).placeholder(emptyImg).into(iv);
    }

    /**
     * 加载drawable中的图片资源 圆角
     *
     * @param context  context
     * @param iv       imageView
     * @param resId    图片地址
     * @param emptyImg 默认展位图
     * @param radius   圆角 20
     */
    public static void loadRoundImage(Context context, ImageView iv, int resId, int emptyImg, int radius) {
        GlideApp.with(context).load(emptyImg).placeholder(emptyImg).transform(new RoundedCorners(radius)).into(iv);
    }

    /**
     * 加载drawable中的图片资源 圆形
     *
     * @param context  context
     * @param iv       imageView
     * @param resId    图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadCircleImage(Context context, ImageView iv, int resId, int emptyImg) {
        GlideApp.with(context).load(emptyImg).placeholder(emptyImg).transform(new CircleCrop()).into(iv);
    }


    /**
     * 加载Base64图片
     *
     * @param context  context
     * @param iv       imageView
     * @param base64   base64图片
     * @param emptyImg 默认展位图
     */
    public static void loadBase64Image(Context context, ImageView iv, String base64, int emptyImg) {
        if (!TextUtils.isEmpty(base64)) {
            GlideApp.with(context)
                    .load(ConverterUtils.base64Tobyte(base64))
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(iv);
        } else {
            loadImage(context, iv, emptyImg, emptyImg);
        }
    }

    /**
     * 加载Base64圆角图片
     *
     * @param context  context
     * @param iv       imageView
     * @param base64   base64图片地址
     * @param emptyImg 默认展位图
     * @param radius   圆角图片 20
     */
    public static void loadBase64RoundImage(Context context, ImageView iv, String base64, int emptyImg, int radius) {
        if (!TextUtils.isEmpty(base64)) {
            GlideApp.with(context)
                    .load(ConverterUtils.base64Tobyte(base64))
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .transform(new RoundedCorners(radius)).into(iv);
        } else {
            loadRoundImage(context, iv, emptyImg, emptyImg, radius);
        }
    }

    /**
     * 加载Base64圆形图片
     *
     * @param context  context
     * @param iv       imageView
     * @param base64   base64图片地址
     * @param emptyImg 默认展位图
     */
    public static void loadBase64CircleImage(Context context, ImageView iv, String base64, int emptyImg) {
        if (!TextUtils.isEmpty(base64)) {
            GlideApp.with(context)
                    .load(ConverterUtils.base64Tobyte(base64))
                    .error(emptyImg)
                    .placeholder(iv.getDrawable())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .transform(new CircleCrop()).into(iv);
        } else {
            loadCircleImage(context, iv, emptyImg, emptyImg);
        }
    }

}
