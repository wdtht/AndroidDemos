package com.example.androiddemos.customview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;

public class CustomViewActivity extends BaseActivity {
    private Button progressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }



    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, CustomViewActivity.class);
        context.startActivity(starter);
    }
}