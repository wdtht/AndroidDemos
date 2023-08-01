package com.example.androiddemos.customview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddemos.R;

import java.util.List;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/8/1
 */
public class DragListAdapter extends RecyclerView.Adapter<DragListAdapter.MyViewHolder>{

    private Context mContext;
    List<String> list;

    public DragListAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list=list;
    }

    /**
     * 设置布局
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler,viewGroup,false));
        return holder;
    }

    /**
     * 为控件绑定数据
     * @param myViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int position) {

        myViewHolder.tv_name.setText(list.get(position));

    }

    /**
     * 返回项个数
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 定义控件并初始化
     */
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
