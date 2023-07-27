package com.example.androiddemos.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/27
 */
open class BaseViewModel : ViewModel() {

    val showToast = MutableLiveData<Int>()
    val loading = MutableLiveData<Boolean>()

    fun <T> CoroutineScope.dataLaunch(init: CoroutineBuilder<T>.() -> Unit) {
        val result = CoroutineBuilder<T>().apply(init)
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            result.onError?.invoke(exception)
            exception.printStackTrace()
            loading.postValue(false)
        }
        launch(coroutineExceptionHandler) {
            val res: T? = result.onRequest?.invoke()
            res?.let {
                result.onSuccess?.invoke(it)
            }
            loading.postValue(false)
        }
    }

    class CoroutineBuilder<T> {
        var onRequest: (suspend () -> T)? = null
        var onSuccess: ((T) -> Unit)? = null
        var onError: ((Throwable) -> Unit)? = null
    }

}