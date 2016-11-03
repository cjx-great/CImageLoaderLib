package com.c_imageloader.configer;

import com.c_imageloader.cache.MemoryCache;
import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.interfaces.LoadPolicy;
import com.c_imageloader.policy.SerialPolicy;

/**
 * Created by CJX on 2016/10/18.
 */

public class CImageLoaderConfig {

    private ImageCache imageCache;

    private DisplayConfig displayConfig;

    private LoadPolicy loadPolicy;

    private int threadCount;

    public static class Builder{

        /**
         * 缓存类型
         *
         * 默认内存
         * */
        private ImageCache imageCache = new MemoryCache();

        /**
         * 加载中以及加载失败的图片显示配置
         * */
        private DisplayConfig displayConfig = new DisplayConfig();

        /**
         * 加载策略
         *
         * 默认顺序加载
         * */
        private LoadPolicy loadPolicy = new SerialPolicy();

        /**
         * 线程池线程数量
         * */
        private int threadCount = Runtime.getRuntime().availableProcessors() + 1;

        /**
         * 供用户自定义缓存类型
         * */
        public Builder setImageCache(ImageCache imageCache) {
            this.imageCache = imageCache;
            return this;
        }

        /**
         * 供用户自定义加载中默认显示图片
         * */
        public Builder setLoadingImage(int resID){
            displayConfig.setLoadingResID(resID);
            return this;
        }

        /**
         * 供用户自定义加载失败默认显示图片
         * */
        public Builder setFailedImage(int resID){
            displayConfig.setFailedResID(resID);
            return this;
        }

        /**
         * 供用户自定义加载策略(模式)
         * */
        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            if (loadPolicy != null){
                this.loadPolicy = loadPolicy;
            }
            return this;
        }

        /**
         * 供用户自定义线程池线程数目
         * */
        public Builder setThreadCount(int threadCount) {
            this.threadCount = Math.max(1,threadCount);
            return this;
        }

        public CImageLoaderConfig build(){
            CImageLoaderConfig config = new CImageLoaderConfig();
            //应用配置
            apply(config);
            return config;
        }

        private void apply(CImageLoaderConfig config){
            config.imageCache = this.imageCache;
            config.displayConfig = this.displayConfig;
            config.loadPolicy = this.loadPolicy;
            config.threadCount = this.threadCount;
        }
    }

    public ImageCache getImageCache() {
        return imageCache;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }
}
