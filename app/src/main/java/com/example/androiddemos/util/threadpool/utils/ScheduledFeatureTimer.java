package com.example.androiddemos.util.threadpool.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时器
 *
 * @author : chenjing 2020/10/12
 */
public class ScheduledFeatureTimer {

    private ScheduledExecutorService mService;
    private ScheduledFuture<?> mFeature;
    private String mThreadName = "ScheduledFeatureTimer";
    private boolean mIsDaemon = false;

    public ScheduledFeatureTimer() {
    }

    public ScheduledFeatureTimer(@NonNull String threadName) {
        mThreadName = threadName;
    }

    public ScheduledFeatureTimer(@NonNull String threadName, boolean isDaemon) {
        mThreadName = threadName;
        mIsDaemon = isDaemon;
    }

    public void startTimer(@NonNull Runnable command, long delay) {
        stopTimer();
        mService = Executors.newScheduledThreadPool(1, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName(mThreadName);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setDaemon(mIsDaemon);
            return thread;
        });
        mFeature = mService.schedule(command, delay, TimeUnit.MILLISECONDS);
    }

    public void startTimer(@NonNull Runnable command, long delay, long period) {
        stopTimer();
        mService = Executors.newScheduledThreadPool(1, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("ScheduledFeatureTimer");
            thread.setPriority(Thread.MIN_PRIORITY);
            return thread;
        });
        mFeature = mService.scheduleAtFixedRate(command, delay, period, TimeUnit.MILLISECONDS);
    }

    public void stopTimer() {
        if (mFeature != null) {
            mFeature.cancel(true);
            mFeature = null;
        }
        if (mService != null)
            mService.shutdown();
        mService = null;
    }

}
