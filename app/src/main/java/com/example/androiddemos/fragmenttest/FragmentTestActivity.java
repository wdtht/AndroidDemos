package com.example.androiddemos.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.androiddemos.R;

public class FragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat);
        setContentView(R.layout.activity_fragment_test);
        Button progressBtn = findViewById(R.id.progress_btn);
        progressBtn.setOnClickListener(view -> start());
    }

    public  void start(){
        ProgressPercentFragment progressPercentFragment;
        progressPercentFragment = new ProgressPercentFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main,progressPercentFragment);
        fragmentTransaction.commit();
    }
}