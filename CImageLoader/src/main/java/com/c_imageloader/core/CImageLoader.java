package com.c_imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.c_imageloader.configer.CImageLoaderConfig;
import com.c_imageloader.configer.DisplayConfig;
import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.request.BitmapRequest;
import com.c_imageloader.request.RequestQuene;

/**
 * Created by CJX on 2016/10/18.
 */

public final class CImageLoader {

    /**
     * 网络请求队列
     * */
    private RequestQuene requestQuene = null;

    //配置
    private CImageLoaderConfig config = null;

    //缓存
    private volatile ImageCache imageCache = null;

    private CImageLoader(){};

    /**
     * 静态内部类实现单例
     * */
    private static class CImageLoaderHolder{
        private static final CImageLoader loader = new CImageLoader();
    }

    public static CImageLoader getInatance(){
        return CImageLoaderHolder.loader;
    }

    /**
     * 提供给用户将配置应用到ImageLoader上面
     * */
    public void init(CImageLoaderConfig config){
        this.config = config;
        this.imageCache = config.getImageCache();

        checkConfig();

        //初始化请求队列
        requestQuene = new RequestQuene(config.getThreadCount());
        //开始运行
        requestQuene.start();
    }

    /**
     * 检查用户自定义配置
     * */
    private void checkConfig(){
        if (config == null){
            throw new RuntimeException("The config of CImageLoader is Null");
        }
    }

    public void displayImag(String url, ImageView imageView){
        this.displayImag(url,imageView,null,null);
    }

    public void displayImag(String url, ImageView imageView,DisplayConfig displayConfig){
        this.displayImag(url,imageView,displayConfig,null);
    }

    public void displayImag(String url, ImageView imageView,OnLoadListener onLoadListener){
        this.displayImag(url,imageView,null,onLoadListener);
    }


    public void displayImag(String url, ImageView imageView,
                            DisplayConfig displayConfig,OnLoadListener onLoadListener){
        BitmapRequest request = new BitmapRequest(url,imageView,displayConfig,onLoadListener);
        //如果没有设置配置则使用初始化时的配置
        request.displayConfig = (request.displayConfig != null ? request.displayConfig : this.config.getDisplayConfig());
        //添加到队列中
        requestQuene.add(request);
    }

    public CImageLoaderConfig getConfig() {
        return config;
    }

    /**
     * 提供用户停止所有的请求处理
     * */
    public void stopRequest(){
        requestQuene.stop();
    }

    /**
     * 提供给用户注销开源库
     * */
    public void withdraw(){
        requestQuene.clear();
    }

    /**
     * 供用户对加载成功后的状态进行操作
     * */
    public interface OnLoadListener{
        void onCompleted(String url, ImageView imageView, Bitmap bitmap);
    }

}
