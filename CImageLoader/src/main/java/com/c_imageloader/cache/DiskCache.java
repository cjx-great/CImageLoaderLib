package com.c_imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import com.c_imageloader.interfaces.ImageCache;
import com.c_imageloader.request.BitmapRequest;
import com.c_imageloader.utils.CloseUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by CJX on 2016/10/18.
 */

public class DiskCache implements ImageCache {

    /**
     * 缓存目录文件夹
     * */
    private static final String CACHE_DIR = "CCache";

    private static DiskCache diskCache = null;

    private static String DIR = "";

    private DiskCache(Context context) {
        DIR = getCacheFilePath(context) + File.separator + CACHE_DIR;
        File cacheDir = new File(DIR);
        if (!cacheDir.exists()){
            cacheDir.mkdir();
        }

    }

    //获取缓存根目录
    private String getCacheFilePath(Context context){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //应用缓存目录
            cachePath = context.getCacheDir().getPath();
        }

        return cachePath;
    }

    /**
     * DCL实现单例
     * */
    public static DiskCache getInstance(Context context){
        if (diskCache == null){
            synchronized (DiskCache.class){
                if (diskCache == null){
                    diskCache = new DiskCache(context);
                }
            }
        }

        return diskCache;
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return BitmapFactory.decodeFile(DIR + File.separator + key.getImageUrl() + ".jpg");
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
        File file = new File(DIR + File.separator + key.getImageUrl() + ".jpg");
        try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            value.compress(Bitmap.CompressFormat.JPEG,100,output);
            try {
                output.flush();
                CloseUtils.close(output);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(BitmapRequest key) {
        File file = new File(DIR + File.separator + key.getImageUrl() + ".jpg");
        if (file.exists()){
            file.delete();
        }
    }
}
