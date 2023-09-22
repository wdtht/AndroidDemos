package com.example.androiddemos.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddemos.R;
import com.example.androiddemos.network.Interface.ItemData;

import java.util.Collections;
import java.util.List;

/**
 * 作用：导航线快速排序adapter
 *
 * @author chenkexi
 * @date :2023/9/21
 */

public class NavLineRecyleAdapter extends RecyclerView.Adapter<NavLineRecyclerHolder> {
    private final Context context;
    private static String TAG = "NavLineRecyleAdapter";
    private List<ItemData> itemList;
    private final OnUsingClickListener onUsingClickListener;

    private OnItemClickListener onItemClickListener;
    private int selectedPos = RecyclerView.NO_POSITION; // 初始化选中位置

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnUsingClickListener {
        void onUsingClick(int position);
    }

    public NavLineRecyleAdapter(Context context, List<ItemData> itemList, OnUsingClickListener onUsingClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onUsingClickListener = onUsingClickListener;
    }

    @NonNull
    @Override
    public NavLineRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.navline_item, parent, false);
        return new NavLineRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavLineRecyclerHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemData item = itemList.get(position);
        setIcon(position, holder);
        holder.tvTitle.setText(item.title);
        holder.tvContent.setText(item.content);
        holder.tvUsing.setOnClickListener(view -> {
            //using点击事件
            if (view.getVisibility() == View.VISIBLE) {
                onUsingClickListener.onUsingClick(position);
            }
        });
        setSelectView(position, holder);
        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG,"itemView click!");
            notifyItemChanged(selectedPos); // 刷新之前的选中项
            selectedPos = holder.getAdapterPosition(); // 更新选中项
            notifyItemChanged(selectedPos); // 刷新当前选中项
            //回调出去的位置信息
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    //设置选中颜色和using的显示
    private void setSelectView(int position, NavLineRecyclerHolder holder) {
        if (selectedPos == position) {
            holder.tvUsing.setVisibility(View.VISIBLE);
            holder.itemView.setSelected(true);
        } else {
            holder.tvUsing.setVisibility(View.INVISIBLE);
            holder.itemView.setSelected(false);
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(itemList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        // 更新选中状态
        updateSelectedPosAfterMove(fromPosition, toPosition);
    }

    public void updateSelectedPosAfterMove(int fromPosition, int toPosition) {
        if (selectedPos == fromPosition) {
            selectedPos = toPosition;
        } else if (selectedPos == toPosition) {
            selectedPos = fromPosition;
        }
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
    }

    public void setList(List<ItemData> list) {
        this.itemList = list;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setIcon(int position, NavLineRecyclerHolder holder) {
        switch (position) {
            case 0:
                holder.ivIcon.setVisibility(View.VISIBLE);
                holder.ivIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.one));
                break;
            case 1:
                holder.ivIcon.setVisibility(View.VISIBLE);
                holder.ivIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.two));
                break;
            case 2:
                holder.ivIcon.setVisibility(View.VISIBLE);
                holder.ivIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.three));
                break;
            default:
                holder.ivIcon.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void removeAllClickListener() {
        this.onItemClickListener = null;
    }
}
