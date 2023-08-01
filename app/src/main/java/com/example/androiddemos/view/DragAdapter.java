package com.example.androiddemos.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class DragAdapter<T> extends BaseAdapter {

    private static final String TAG = "DragAdapter";

    private List<T> mData;
    private int mHidePosition = -1;

    public DragAdapter(List<T> data) {
        mData = data;
    }

    public void reorderItems(int oldPos, int newPos) {
        try {
            if (oldPos < 0 || oldPos >= mData.size() || newPos < 0 || newPos >= mData.size()) {
                Log.e(TAG, "Invalid positions");
                return;
            }
            T temp = mData.get(oldPos);
            mData.remove(oldPos);
            mData.add(newPos, temp);
            mHidePosition = newPos;
        } catch (Exception e) {
            Log.e(TAG, "Exception in reorderItems", e);
        }
    }

    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Abstract method, for subclasses to implement
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
