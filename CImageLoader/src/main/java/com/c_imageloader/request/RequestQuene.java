package com.c_imageloader.request;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by CJX on 2016/10/18.
 */

public class RequestQuene {

    /**
     * 请求队列
     *
     * 优先级队列
     * */
    //存储的对象必须是实现Comparable接口。队列通过这个接口的compareTo方法确定对象的priority。
    private BlockingQueue<BitmapRequest> requestQueue = new PriorityBlockingQueue<>();

    /**
     * 序列化生成器
     *
     * 保证线程安全
     * */
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 默认线程核心数
     * */
    public static int DEFAULT_THREAD_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * 处理请求线程数
     * */
    private int handlerCount = DEFAULT_THREAD_NUMS;

    /**
     * 多线程
     *
     * 执行请求
     * */
    private RequestHandler[] handlers = null;

    public RequestQuene() {
        this(DEFAULT_THREAD_NUMS);
    }

    public RequestQuene(int threadCount) {
        this.handlerCount = threadCount;
    }


    //在CImageLoader初始化时调用
    //生成线程处理请求
    public void start(){
        stop();
        startHandler();
    }

    /**
     * 停止RequestHandler
     */
    public void stop() {
        if (handlers != null && handlers.length > 0) {
            for (int i = 0; i < handlers.length; i++) {
                handlers[i].interrupt();
            }
        }
    }

    /**
     * 启动RequestHandler
     */
    private void startHandler(){
        handlers = new RequestHandler[handlerCount];
        for (int i = 0; i < handlerCount; i++) {
            handlers[i] = new RequestHandler(requestQueue);
            handlers[i].start();
        }
    }

    /**
     * 添加请求
     */
    public void add(BitmapRequest request) {
        if (!requestQueue.contains(request)) {
            //以原子方式将当前值加1，并返回新值
            request.priority = atomicInteger.incrementAndGet();
            requestQueue.add(request);
        }
    }

    public void clear(){
        requestQueue.clear();
    }

}
