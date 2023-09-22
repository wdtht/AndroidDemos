package com.example.androiddemos.view.adpter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.androiddemos.R;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/9/21
 */

public class NavLineRecyclerHolder extends RecyclerView.ViewHolder {
    public ImageView ivIcon;
    public TextView tvTitle;
    public TextView tvContent;
    public TextView tvUsing;

    public NavLineRecyclerHolder(View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.ivIcon);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvContent = itemView.findViewById(R.id.tvContent);
        tvUsing = itemView.findViewById(R.id.tv_using);
    }
}

