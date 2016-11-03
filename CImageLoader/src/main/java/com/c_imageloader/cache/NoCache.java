package com.c_imageloader.cache;

import android.graphics.Bitmap;

import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/18.
 */

public class NoCache implements ImageCache{


    @Override
    public Bitmap get(BitmapRequest key) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
