package com.c_imageloader.request;

import android.util.Log;

import com.c_imageloader.interfaces.Loader;
import com.c_imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by CJX on 2016/10/22.
 *
 * 执行请求
 */

public class RequestHandler extends Thread{

    /**
     * 请求队列
     * */
    private BlockingQueue<BitmapRequest> requestQueue = null;

    public RequestHandler(BlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;

    }

    @Override
    public void run() {
        super.run();
        while (!this.isInterrupted()){
            try {
                BitmapRequest request = requestQueue.take();
                if (request.isCancel()){
                    continue;
                }

                String type = parseUri(request.getImageUrl());
                //根据type获取Loader
                Loader loader = LoaderManager.getInstance().getLoader(type);
                loader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 从请求中解析，判断是从哪里获取图片
     * */
    private String parseUri(String imageUrl) {
        if (imageUrl.contains("://")){
            return imageUrl.split("://")[0];
        }else{
            Log.d("RequestHandler","Uri格式错误");
        }

        return "";
    }

}
