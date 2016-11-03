package com.c_imageloader.policy;

import com.c_imageloader.interfaces.LoadPolicy;
import com.c_imageloader.request.BitmapRequest;

/**
 * Created by CJX on 2016/10/18.
 *
 * 逆序加载
 */

public class ReversePolicy implements LoadPolicy {

    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request2.priority - request1.priority;
    }
}
