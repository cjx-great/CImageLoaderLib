package com.c_imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by CJX on 2016/11/2.
 *
 * 用于解析bitmap
 */

public class BitmapDecode {

    /**
     * 解析网络上的图片
     * */
    public static Bitmap decodeFromNet(InputStream inputStream, ImageView imageView){
        Bitmap result = null;
        Bitmap source = null;

        //将InputStream存储为byte[]
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream,null,options);

        options.inSampleSize = getInSampleSize(options,imageView.getWidth(),imageView.getHeight());

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        source = BitmapFactory.decodeByteArray(swapStream.toByteArray(),0,swapStream.toByteArray().length,options);

        result = createScaleBitmap(source, imageView.getHeight(), imageView.getWidth(), options.inSampleSize);

        return result;
    }

    /**
     * 获取缩放比率
     * @param widthNeed 需要的宽度
     * @param heightNeed 需要的高度
     * */
    private static int getInSampleSize(BitmapFactory.Options options, int widthNeed, int heightNeed){
        int inSampleSize = 1;

        //Bitmap的宽高
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        if (bitmapHeight > heightNeed || bitmapWidth > widthNeed){
            final int halfBitHeight = bitmapHeight / 2;
            final int halfBitWidth = bitmapWidth / 2;

            while ((halfBitHeight / inSampleSize) > heightNeed
                    && (halfBitWidth / inSampleSize) > widthNeed){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 通过缩放比率创建新的Bitmap
     * */
    private static Bitmap createScaleBitmap(Bitmap source,int height,int width,int inSampleSize){
        //如果inSampleSize是2的倍数，也就说这个source已经是我们想要的缩略图了，直接返回即可。
        if (inSampleSize % 2 == 0) {
            return source;
        }

        // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
        Bitmap dst = Bitmap.createScaledBitmap(source, width, height, false);
        if (source != dst) { // 如果没有缩放，那么不回收
            source.recycle(); // 释放Bitmap的native像素数组
        }

        return dst;
    }
}
