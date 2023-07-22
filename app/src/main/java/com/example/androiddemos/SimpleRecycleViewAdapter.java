package com.example.androiddemos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class SimpleRecycleViewAdapter extends RecyclerView.Adapter<SimpleRecycleViewAdapter.ViewHolder> {
    private ViewData[] viewDataList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recycleview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setText(viewDataList[position].buttonText);
        holder.button.setOnClickListener(viewDataList[position].callbackWeakReference.get());
    }

    @Override
    public int getItemCount() {
        return viewDataList.length;
    }

    public void setViewDataList(ViewData[] viewDataList){
        this.viewDataList = viewDataList;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.button);
        }
    }

    public static class ViewData {
        private final String buttonText;
        private final WeakReference<View.OnClickListener> callbackWeakReference;


        public ViewData(String buttonText, View.OnClickListener onClickListener){
            this.buttonText = buttonText;
            this.callbackWeakReference = new WeakReference<>(onClickListener);
        }
    }
}

