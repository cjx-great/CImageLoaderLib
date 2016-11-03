package com.c_imageloader.loader;

import android.graphics.Bitmap;

import com.c_imageloader.request.BitmapRequest;
import com.c_imageloader.utils.BitmapDecode;
import com.c_imageloader.utils.CloseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CJX on 2016/10/22.
 */

public class URLoader extends ModelLoader {

    private Bitmap bitmap = null;

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        String imageUrl = request.getImageUrl();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(imageUrl);

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                //对Bitmap进行适配
                bitmap = BitmapDecode.decodeFromNet(inputStream, request.getImageView());

                //直接加载图片无法适配Imagview大小
//                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                CloseUtils.close(inputStream);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
