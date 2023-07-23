package com.example.androiddemos.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.customview.activity.CustomViewActivity;
import com.example.androiddemos.main.adapter.SimpleRecycleViewAdapter;
import com.example.androiddemos.network.activity.NetworkActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        SimpleRecycleViewAdapter recycleViewAdapter = new SimpleRecycleViewAdapter();
        String[] adpterData={"custom","network"};
        View.OnClickListener[] onClickListeners = {
                v -> CustomViewActivity.start(this),
                v -> NetworkActivity.start(this)
        };
        List<SimpleRecycleViewAdapter.ViewData> viewData = new ArrayList<>();
        for (int i=0;i<adpterData.length;i++) {
            viewData.add( new SimpleRecycleViewAdapter.ViewData(adpterData[i], onClickListeners[i]));
        }
        recycleViewAdapter.setViewDataList(viewData);
        recyclerView.setAdapter(recycleViewAdapter);
    }
}