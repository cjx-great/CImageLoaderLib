package com.c_imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/18.
 *
 */

public class MemoryCache implements ImageCache {

    //内部采用最近最少使用算法
    private LruCache<String,Bitmap> lruCache = null;

    public MemoryCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 取4分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 4;

        lruCache = new LruCache<String,Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return lruCache.get(key.getImageUrl());
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
        lruCache.put(key.getImageUrl(),value);
    }

    @Override
    public void remove(BitmapRequest key) {
        lruCache.remove(key.getImageUrl());
    }
}
