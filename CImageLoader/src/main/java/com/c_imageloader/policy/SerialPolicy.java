package com.c_imageloader.policy;

import com.c_imageloader.interfaces.LoadPolicy;
import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/18.
 *
 * 顺序加载
 */

public class SerialPolicy implements LoadPolicy {

    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request1.priority - request2.priority;
    }
}
