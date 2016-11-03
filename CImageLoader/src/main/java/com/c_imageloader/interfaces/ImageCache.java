package com.c_imageloader.interfaces;

import android.graphics.Bitmap;

import com.c_imageloader.request.BitmapRequest;


/**
 * Created by CJX on 2016/10/18.
 *
 * 定义缓存接口
 */

public interface ImageCache {

    /**
     * 通过url来获取Bitmap,进一步显示
     * */
    Bitmap get(BitmapRequest key);
    /**
     * 缓存Bitmap
     * */
    void put(BitmapRequest key, Bitmap value);
    /**
     * 移除缓存
     * */
    void remove(BitmapRequest key);
}
