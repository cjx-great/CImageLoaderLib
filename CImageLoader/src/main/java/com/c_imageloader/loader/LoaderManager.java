package com.c_imageloader.loader;


import com.c_imageloader.interfaces.Loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJX on 2016/10/22.
 */

public class LoaderManager {

    private static LoaderManager loaderManager = null;

    private static Map<String, Loader> loaderMap = new HashMap<>();

    /**
     * 默认Loader
     * */
    private Loader defaultLoader = new NoLoader();

    private LoaderManager() {
        register("http", new URLoader());
        register("https", new URLoader());
    }

    /**
     * DCL实现单例
     * */
    public static LoaderManager getInstance(){
        if (loaderManager == null){
            synchronized (LoaderManager.class){
                if (loaderManager == null){
                    loaderManager = new LoaderManager();
                }
            }
        }

        return loaderManager;
    }

    /**
     * 加载具体Loader
     *
     * 用户可以自定义
     *
     * @param type 加载类型
     * */
    public synchronized void register(String type, Loader loader) {
        loaderMap.put(type, loader);
    }

    /**
     * 提供给用户获取内置Loader
     * */
    public Loader getLoader(String type){
        if (loaderMap.containsKey(type)){
            return loaderMap.get(type);
        }

        return defaultLoader;
    }
}
