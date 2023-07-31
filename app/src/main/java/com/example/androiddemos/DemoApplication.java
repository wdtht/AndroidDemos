package com.example.androiddemos;

import android.app.Application;
import android.util.Log;

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
    /**
     * 获取App实例
     *
     * @return App 实例
     */
    public static DemoApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG","#onCreate-----");
        //mmkv 存储键值对，初始化
        MMKV.initialize(this);
    }
}
