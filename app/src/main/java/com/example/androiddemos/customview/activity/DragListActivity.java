package com.example.androiddemos.customview.activity;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.customview.adapter.DragListAdapter;
import com.example.androiddemos.util.MyItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragListActivity extends BaseActivity {
    private DragListAdapter rv_1_homeAdapter;
    private final String TAG = "superdemo/DragListActivity";
    RecyclerView rv_1;
    LinearLayoutManager rv_1_Manager;
    ItemTouchHelper mItemTouchHelper;
    List<String> titles;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list);
        rv_1=findViewById(R.id.rv_1);

        titles = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            titles.add("中文"+i);
        }


        rv_1_homeAdapter = new DragListAdapter(getApplicationContext(),titles);
        rv_1_Manager=new LinearLayoutManager(getApplicationContext());
        //这里使用垂直滑动
        rv_1_Manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_1.setLayoutManager(rv_1_Manager);
        rv_1.setAdapter(rv_1_homeAdapter);

        //添加拖拽事件                                                                 longClickPosition：初始索引值            ActionUpPosition：结束索引值
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper(this,titles,rv_1_homeAdapter,(longClickPosition, ActionUpPosition)-> {
            if (longClickPosition==-1)return;

            //用swap方法可以交换集合中任意两个元素的位置
            Collections.swap(titles,longClickPosition,ActionUpPosition);

            //列表的适配器
            rv_1_homeAdapter.notifyDataSetChanged();

        }));
        //在这里给列表添加移动顺序
        mItemTouchHelper.attachToRecyclerView(rv_1);
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, DragListActivity.class);
        context.startActivity(starter);
    }
}