package com.example.androiddemos.util;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddemos.customview.activity.DragListActivity;
import com.example.androiddemos.customview.adapter.DragListAdapter;

import java.util.Collections;
import java.util.List;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/8/1
 */
public class MyItemTouchHelper extends ItemTouchHelper.Callback {
    private List<String> list;
    private final String TAG = "superdemo/MyItemTouchHelper";
    private DragListAdapter recycleViewAdapter;
    private RequestDouble<Integer, Integer> mRequestResult;
    private int longClickPosition = -1;
    DragListActivity activity;

    public MyItemTouchHelper(DragListActivity activity, List<String> list, DragListAdapter recycleViewAdapter, RequestDouble<Integer, Integer> requestResult) {
        Log.d(TAG, "into MyItemTouchHelper");
        this.list = list;
        this.recycleViewAdapter = recycleViewAdapter;
        this.activity = activity;
        this.mRequestResult = requestResult;
    }

    //设置拖拽和item滑动的可支持方向
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //这里是设置列表水平拖动
        //   final int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;  //ItemTouchHelper.UP | ItemTouchHelper.DOWN |
        //这里是设置列表是垂直拖动
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;  //ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |

        //这里是设置网格布局拖动
        //   final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        final int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }


    /**
     * 拖拽结束后（手指抬起）会回调的方法
     *
     * @param recyclerView
     * @param viewHolder   手指拖拽的item
     * @param target       移动到的item
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // 我们在该方法中实现item数据在数据集合中的位置交换，并调用Adapter的notifyItemMoved完成item界面刷新
        recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();
        Log.d(TAG, "fromPosition："+fromPosition);
        Log.d(TAG, "toPosition："+toPosition);
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                //这里暂时注释，用外面的数据来进行交换
                 Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                //这里暂时注释，用外面的数据来进行交换
                Collections.swap(list, i, i - 1);
            }
        }
        int movementFlags = getMovementFlags(recyclerView, viewHolder);
        Log.d(TAG, " movementFlags = " + movementFlags);
        //通知适配器改变位置
        recycleViewAdapter.notifyItemMoved(fromPosition, toPosition);
        Log.d(TAG, " fromPosition = " + fromPosition + " toPosition" + toPosition);
        return true;
    }

    /**
     * 侧滑回调
     *
     * @param viewHolder
     * @param direction  方向
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 长按选中Item时修改颜色
     *
     * @param viewHolder
     * @param actionState
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
        //viewHolder.itemView.setBackground(getDrawable(R.drawable.card_drag_selected));
        //}
        if (viewHolder != null) {
            longClickPosition = viewHolder.getAdapterPosition();
            Log.d("adapterPosition=====", "adapterPosition22222=" + longClickPosition);
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 手指松开的时候还原颜色
     *
     * @param recyclerView
     * @param viewHolder
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int adapterPosition = viewHolder.getAdapterPosition();
        if (mRequestResult != null) {
            //longClickPosition：这是初始索引值            adapterPosition：这是结束索引值
            mRequestResult.result(longClickPosition, adapterPosition);
        }
        Log.d("adapterPosition=====", "adapterPosition=" + adapterPosition);
        super.clearView(recyclerView, viewHolder);
        //viewHolder.itemView.setBackground(getDrawable(R.drawable.card));
    }

    /**
     * 重写拖拽不可用
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        //开启长按后开始拖拽的效果
        return true;
    }

    public interface RequestDouble<Integer1, Integer2> {
        void result(Integer1 integer1, Integer2 integer2);

    }

}
