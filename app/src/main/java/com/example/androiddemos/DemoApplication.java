package com.example.androiddemos;

import android.app.Application;
import android.util.Log;

import com.didi.drouter.api.DRouter;
import com.example.androiddemos.util.threadpool.LogCallback;
import com.example.androiddemos.util.threadpool.PoolThread;
import com.tencent.mmkv.MMKV;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/29
 */
public class DemoApplication extends Application {
    private static final String TAG = "superdemo/DemoApplication";
    private static DemoApplication instance;
    private PoolThread executor;
    /**
     * 获取App实例
     *
     * @return App 实例
     */
    public static DemoApplication getInstance() {
        return instance;
    }

    /**
     * 获取线程池管理器对象，统一的管理器维护所有的线程池
     *
     * @return executor对象
     */
    public PoolThread getExecutor() {
        if (executor == null) {
            executor = PoolThread.ThreadBuilder
                    .createFixed(8)
                    .setPriority(Thread.MAX_PRIORITY)
                    .setCallback(new LogCallback())
                    .build();
        }
        return executor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG","#onCreate-----");
        //mmkv 存储键值对，初始化
        instance=this;
        DRouter.init(this);
        MMKV.initialize(this);
    }
}
