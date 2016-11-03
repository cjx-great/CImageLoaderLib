package com.c_imageloader.interfaces;


import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/22.
 *
 * 定义加载接口
 */

public interface Loader {
    void loadImage(BitmapRequest request);
}
