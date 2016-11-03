package com.c_imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.c_imageloader.configer.DisplayConfig;
import com.c_imageloader.core.CImageLoader;
import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.interfaces.Loader;
import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/22.
 *
 *  模板方法模式
 */

public abstract class ModelLoader implements Loader {

    /**
     * 图片缓存
     * */
    private static ImageCache imageCache = CImageLoader.getInatance().getConfig().getImageCache();

    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap bitmap = imageCache.get(request);

        if (bitmap == null){
            showLoding(request);
            //模板方法
            bitmap = onLoadImage(request);
            //进行缓存
            cacheBitmap(request,bitmap);
        }

        sendToUI(request,bitmap);
    }

    /**
     * 显示加载中图片
     * */
    private void showLoding(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        //判断Tag与Uri是否一样，防止错位
        if (request.isUriEqualsTag() && hasLoadingConfig(request.displayConfig)){
            //操作UI线程的一种方法
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.getLoadingResID());
                }
            });
        }
    }

    /**
     * 将加载获取的bitmap缓存
     * */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null && imageCache != null){
            //防止多线程同时操作
            synchronized (imageCache){
                imageCache.put(request,bitmap);
            }
        }
    }

    /**
     * 将获取到的图片交由UI处理
     * */
    private void sendToUI(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView == null){
            return;
        }

        imageView.post(new Runnable() {
            @Override
            public void run() {
                update(request,bitmap);
            }
        });
    }

    /**
     * 更新Imagview的图片
     * */
    private void update(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (bitmap != null && request.isUriEqualsTag()){
            imageView.setImageBitmap(bitmap);
        }

        //加载失败
        if (bitmap == null && hasFailedConfig(request.displayConfig)){
            imageView.setImageResource(request.displayConfig.getFailedResID());
        }

        //加载完成，回调接口
        if (request.getOnLoadListener() != null){
            request.getOnLoadListener().onCompleted(request.getImageUrl(),imageView,bitmap);
        }
    }

    /**
     * 判断是否设置有加载中显示图片
     * */
    private boolean hasLoadingConfig(DisplayConfig displayConfig){
        if (displayConfig != null && displayConfig.getLoadingResID() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断是否设置有加载失败显示图片
     * */
    private boolean hasFailedConfig(DisplayConfig displayConfig){
        if (displayConfig != null && displayConfig.getFailedResID() > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 具体子类实现如何加载图片
     * */
    protected abstract Bitmap onLoadImage(BitmapRequest request);

}
