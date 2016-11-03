package com.c_imageloader.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by CJX on 2016/10/19.
 */

public final class CloseUtils {

    private CloseUtils() {}

    public static void close(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
