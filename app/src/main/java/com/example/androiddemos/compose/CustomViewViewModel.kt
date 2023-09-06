package com.example.androiddemos.compose

import android.util.Log
import com.example.androiddemos.jetpack.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.IllegalStateException
import java.util.Timer
import java.util.TimerTask

class CustomViewViewModel: BaseViewModel() {
    // 定义一个flow, 用于更新进度, 外界只能读取, 不能设置值, 内部可以设置值
    private val _progress = MutableStateFlow(0f)
    val progress: Flow<Float> = _progress

    private lateinit var timer: Timer

    fun reset() {
        _progress.value = 0f
        if(this::timer.isInitialized) {
            timer.cancel()
        }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                _progress.value += 0.01f
                if (_progress.value >= 1f) {
                    timer.cancel()
                }
            }
        }, 0, 30)
    }

    companion object {
        private const val TAG = "CustomViewViewModel"
    }
}