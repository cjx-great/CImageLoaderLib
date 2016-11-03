package com.c_imageloader.configer;

/**
 * Created by CJX on 2016/10/18.
 *
 * 配置加载中与加载失败的默认显示图片
 */

public class DisplayConfig {

    /**
     * 加载中的图片ID
     * */
    private int loadingResID = -1;

    /**
     * 加载失败的图片ID
     * */
    private int failedResID = -1;

    public void setLoadingResID(int loadingResID) {
        this.loadingResID = loadingResID;
    }

    public void setFailedResID(int failedResID) {
        this.failedResID = failedResID;
    }

    public int getLoadingResID() {
        return loadingResID;
    }

    public int getFailedResID() {
        return failedResID;
    }
}
