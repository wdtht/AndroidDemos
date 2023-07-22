package com.example.androiddemos.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androiddemos.R;

public class FragmentTestActivity extends AppCompatActivity {
    private Button progressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat);
        setContentView(R.layout.activity_fragment_test);
        progressBtn = findViewById(R.id.progress_btn);
        progressBtn.setOnClickListener(view -> start());
    }

    public  void start(){
        ProgressPercentFragment progressPercentFragment;
        progressPercentFragment = new ProgressPercentFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main,progressPercentFragment);
        fragmentTransaction.commit();
        progressBtn.setVisibility(View.GONE);
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, FragmentTestActivity.class);
        context.startActivity(starter);
    }
}