package com.example.androiddemos.jetpack.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.jetpack.model.LivedataModel;
import com.example.androiddemos.jetpack.viewmodel.LivedataViewModel;
import com.example.androiddemos.view.RoundView;
//做一个小游戏，有可以点击变颜色的框，和点击开始自动开始播放颜色的框，点击停止，当两个颜色一致就toast成功，反之失败。
//change.setBackgroundResource(R.drawable.rollbutton_shape);
//上面的圆选择后存进数据库，下面的圆在界面进行判断

public class LivedataMainActivity extends BaseActivity implements View.OnClickListener {
    private LivedataViewModel livedataViewModel;
    private final String TAG = "superdemo/JetpackMainActivity";
    private Button controlButton;
    private RoundView selectView;//下面的圆
    private RoundView changeView;//上面的圆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata_main);
        selectView = (RoundView)findViewById(R.id.select_color);
        changeView = (RoundView)findViewById(R.id.change_color);
        controlButton =(Button) findViewById(R.id.control_btn);
        changeView.setOnClickListener(this);
        controlButton.setOnClickListener(this);
        LivedataModel livedataModel = new LivedataModel();
        initViewModel();
    }

    private void initViewModel() {

        livedataViewModel = new ViewModelProvider(this).get(LivedataViewModel.class);
        livedataViewModel.getChangeColor().observe(this, (String changeColor) -> {
            Log.d(TAG, "changeColor: " + changeColor);
            changeView.setViewColor(Color.parseColor(changeColor));
        });
        livedataViewModel.getSelectColor().observe(this, selectColor -> {
            Log.d(TAG, "selectColor:" + selectColor);
            selectView.setViewColor(Color.parseColor(selectColor));
        });
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, LivedataMainActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.change_color){
            Log.d(TAG, "onClick change_color");
            //setValue()要在主线程中调用，而postValue()既可在主线程也可在子线程中调用
            livedataViewModel.getChangeColor().setValue("#989877");
        } else if (v.getId() == R.id.control_btn) {
            Log.d(TAG, "onClick control_btn");
            livedataViewModel.getSelectColor().setValue("#989877");
        }
    }
}