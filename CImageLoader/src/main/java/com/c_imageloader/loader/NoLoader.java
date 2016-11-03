package com.c_imageloader.loader;

import android.graphics.Bitmap;

import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/22.
 */

public class NoLoader extends ModelLoader {

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        return null;
    }
}
