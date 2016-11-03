package com.c_imageloader.request;

import android.widget.ImageView;

import com.c_imageloader.configer.DisplayConfig;
import com.c_imageloader.core.CImageLoader;
import com.c_imageloader.interfaces.LoadPolicy;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by CJX on 2016/10/18.
 *
 * 为了方便,将图片Url,ImagView,配置,监听 封装为一个请求
 *
 * 请求队列统一调度来实现图片加载
 */

public class BitmapRequest implements Comparable<BitmapRequest>{

    /**
     * 请求优先权
     * */
    public int priority = 0;

    /**
     * 是否取消该请求
     */
    private boolean isCancel = false;

    private Reference<ImageView> imageViewRef = null;

    public DisplayConfig displayConfig = null;

    private CImageLoader.OnLoadListener onLoadListener = null;

    //图片加载链接
    private String imagrUrl = "";

    /**
     * 加载策略
     * */
    private LoadPolicy loadPolicy = CImageLoader.getInatance().getConfig().getLoadPolicy();



    public BitmapRequest(String url, ImageView imageView, DisplayConfig displayConfig,
                         CImageLoader.OnLoadListener onLoadListener) {

        imageViewRef = new WeakReference<>(imageView);
        this.imagrUrl = url;
        this.displayConfig = displayConfig;
        this.onLoadListener = onLoadListener;

        imageView.setTag(imagrUrl);

    }

    /**
     * 判断Url与Tag是否相等
     * */
    public boolean isUriEqualsTag(){
        if (imageViewRef.get() != null){
            if (imageViewRef.get().getTag().equals(imagrUrl)){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }

    @Override
    public int compareTo(BitmapRequest bitmapRequest) {
        return loadPolicy.compare(this,bitmapRequest);
    }

    public String getImageUrl() {
        return imagrUrl;
    }

    public ImageView getImageView(){
        return imageViewRef.get();
    }

    public CImageLoader.OnLoadListener getOnLoadListener() {
        return onLoadListener;
    }

    public boolean isCancel() {
        return this.isCancel;
    }
}
