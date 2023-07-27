package com.example.androiddemos.jetpack.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.androiddemos.bean.ColorBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/27
 */
class JetpackViewModel : BaseViewModel() {
    companion object {
        const val TAG = "superdemo/JetpackViewModel"
    }

    private val _successToastEnabled = MutableStateFlow(false)
    val successToastEnabled: LiveData<Boolean> get() = _successToastEnabled.asLiveData()

    //协程
    private val scope = CoroutineScope(Dispatchers.IO)

    private var _selectColor: String = "#000000"
    private var _changeColor:String? = "#000000"


    suspend fun onEvent(event: ColorBean) {
        Log.i(TAG,"onEvent ColorBean:")
        _changeColor = event.changeColor()
        _selectColor = event.selectColor()
        if (_selectColor == _changeColor){
            _successToastEnabled.emit(true)
        }else{
            _successToastEnabled.emit(false)
        }
    }

}