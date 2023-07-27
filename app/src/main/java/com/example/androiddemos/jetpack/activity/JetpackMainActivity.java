package com.example.androiddemos.jetpack.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.jetpack.model.JetpackModel;
import com.example.androiddemos.jetpack.viewmodel.JetpackViewModel;
//做一个小游戏，有可以点击变颜色的框，和点击开始自动开始播放颜色的框，点击停止，当两个颜色一致就toast成功，反之失败。
//change.setBackgroundResource(R.drawable.rollbutton_shape);
//上面的圆选择后存进数据库，下面的圆在界面进行判断

public class JetpackMainActivity extends BaseActivity {
    private JetpackViewModel jetpackViewModel;
    private final String TAG="superdemo/JetpackMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jetpack_main);
        initViewModel();
        JetpackModel jetpackModel = new JetpackModel();
        jetpackModel.init();
    }

    private void initViewModel(){
        jetpackViewModel = new ViewModelProvider(this).get(JetpackViewModel.class);
        jetpackViewModel.getSuccessToastEnabled().observe(this,  successToastEnabled-> {
            Log.d(TAG,"IsSuccess:"+successToastEnabled);

            runOnUiThread(() -> {
                if(successToastEnabled){
                    Toast.makeText(this,"IsSuccess", Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(this,"fail3333333333",Toast.LENGTH_LONG);
                }
            });


        });
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, JetpackMainActivity.class);
        context.startActivity(starter);
    }
}