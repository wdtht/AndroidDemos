package com.example.androiddemos.jetpack.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/28
 */
public class LivedataViewModel extends BaseViewModel{
    //创建两个MutableLiveData 存储被选中的颜色和在改变的颜色
    private MutableLiveData<String> selectColor;
    private MutableLiveData<String> changeColor;
    private String TAG="superdemo/LivedataViewModel";

    public MutableLiveData<String> getSelectColor() {
        if (selectColor == null) {
            selectColor = new MutableLiveData<>();
        }
        Log.d(TAG, "selectColor:"+selectColor);
        return selectColor;
    }

    public MutableLiveData<String> getChangeColor() {
        if (changeColor == null) {
            changeColor = new MutableLiveData<>();
        }
        Log.d(TAG, "changeColor:"+changeColor);
        return changeColor;
    }
}
