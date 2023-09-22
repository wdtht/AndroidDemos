package com.example.androiddemos.view;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddemos.network.Interface.ItemData;
import com.example.androiddemos.view.adpter.NavLineRecyleAdapter;

import java.util.List;

/**
 * 作用：导航线recycleview管理类,可以拖拽
 *
 * @author chenkexi
 * @date :2023/9/22
 */

@SuppressWarnings("unused")
public class NavLineRecycleViewManage {
    private final String TAG = "AgNav5/NavLineRecycleViewManage";
    private Context myContext;
    private RecyclerView recyclerView;

    private NavLineRecyleAdapter navLineRecyleAdapter;
    ItemTouchHelper mItemTouchHelper;

    List<ItemData> listData;
    private orderListListener mOrderListListener;
    private int startDragPosition = -1;
    private int endDragPosition = -1;

    public interface orderListListener {
        void orderList(int start, int end);
    }

    public NavLineRecycleViewManage(Context context, RecyclerView recyclerView) {
        //拿到主界面的recycleview
        this.myContext = context;
        this.recyclerView = recyclerView;
    }

    //初始recyclerView
    public void initRecyclerView(List<ItemData> list) {
        if (recyclerView == null && myContext == null && list == null) {
            return;
        }
        this.listData = list;  // 使用传入的 list
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(myContext));
        navLineRecyleAdapter = new NavLineRecyleAdapter(myContext, this.listData, position -> {
            // using点击事件
            Log.d(TAG, "Clicked onUsingClick at position " + position);
        });
        recyclerView.setAdapter(navLineRecyleAdapter);
        navLineRecyleAdapter.setOnItemClickListener(position -> {
            Log.d(TAG, "Clicked item at position " + position);
        });
    }


    public void initTouchMove() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                navLineRecyleAdapter.onItemMove(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            // 添加这个方法，使得高亮能跟随手势
            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    startDragPosition = viewHolder.getAdapterPosition();
                    navLineRecyleAdapter.setSelectedPos(startDragPosition);
                }
            }

            // 添加这个方法，去除高亮
            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                endDragPosition = viewHolder.getAdapterPosition(); // 记录结束拖动的位置
                //navLineRecyleAdapter.clearSelectedPos();
                if (mOrderListListener != null) {
                    // 这里提供开始位置和结束位置的结果
                    Log.d(TAG,"startDragPosition:"+startDragPosition+" endDragPosition:"+endDragPosition);
                    mOrderListListener.orderList(startDragPosition,endDragPosition);
                    startDragPosition = -1;
                    endDragPosition = -1; // 重置位置信息
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }



    public void orderListListener(orderListListener orderListListener) {
        mOrderListListener = orderListListener;
    }


    public void smoothScrollToPosition(int position) {
        Log.d(TAG, "#smoothScrollToPosition position: " + position);
        if (position >= 0) {
            recyclerView.smoothScrollToPosition(position);
        }
    }

    public void setListData(List<ItemData> listData) {
        this.listData = listData;
    }

    // 资源释放
    public void releaseResources() {
        // 适当的资源释放
        navLineRecyleAdapter.removeAllClickListener();
        navLineRecyleAdapter = null;
        recyclerView.setAdapter(null);
        recyclerView = null;
        myContext = null;
    }
}

