package com.example.androiddemos.jetpack.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
//做一个小游戏，有可以点击变颜色的框，和点击开始自动开始播放颜色的框，点击停止，当两个颜色一致就toast成功，反之失败。
//change.setBackgroundResource(R.drawable.rollbutton_shape);

public class JetpackMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jetpack_main);
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