package com.example.androiddemos.network.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.customview.activity.CustomViewActivity;

public class NetworkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkActivity.class);
        context.startActivity(starter);
    }
}