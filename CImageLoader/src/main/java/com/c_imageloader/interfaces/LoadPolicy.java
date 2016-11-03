package com.c_imageloader.interfaces;


import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/18.
 *
 * 定义加载策略接口
 */

public interface LoadPolicy {

    /**
     * 比较两个请求的优先权
     * */
    int compare(BitmapRequest request1, BitmapRequest request2);
}
