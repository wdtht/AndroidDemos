package com.example.androiddemos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.androiddemos.fragmenttest.FragmentTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SimpleRecycleViewAdapter recycleViewAdapter = new SimpleRecycleViewAdapter();
        recycleViewAdapter.setViewDataList(new SimpleRecycleViewAdapter.ViewData[]{
                new SimpleRecycleViewAdapter.ViewData("fragment", (View view) -> FragmentTestActivity.start(this))
        });
        recyclerView.setAdapter(recycleViewAdapter);
    }
}