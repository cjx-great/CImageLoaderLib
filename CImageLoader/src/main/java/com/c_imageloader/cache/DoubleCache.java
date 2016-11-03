package com.c_imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/18.
 *
 * 二级缓存
 */

public class DoubleCache implements ImageCache {

    private DiskCache diskCache = null;
    private MemoryCache memoryCache = null;

    public DoubleCache(Context context) {
        memoryCache = new MemoryCache();
        diskCache = DiskCache.getInstance(context);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        Bitmap bitmap = memoryCache.get(key);
        if (bitmap == null){
            bitmap = diskCache.get(key);
            if (bitmap != null){
                //图片加载到内存
                memoryCache.put(key,bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
        memoryCache.put(key, value);
        diskCache.put(key, value);
    }

    @Override
    public void remove(BitmapRequest key) {
        memoryCache.remove(key);
        diskCache.remove(key);
    }
}
