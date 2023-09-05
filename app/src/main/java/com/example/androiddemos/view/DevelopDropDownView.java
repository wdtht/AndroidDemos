package com.example.androiddemos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.androiddemos.R;

import java.util.ArrayList;
import java.util.List;

public class DevelopDropDownView extends LinearLayout {

    private TextView editText;
    private ImageView imageView;
    private PopupWindow popupWindow = null;
    private List<String> dataList = new ArrayList<>();
    private onClickListener mOnClickListener;
    private DropDownListAdapter mDropDownListAdapter;


    public interface onClickListener {
        void setOnClickListener(int position, String t);
    }

    public DevelopDropDownView(Context context) {
        this(context, null);
    }

    public DevelopDropDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DevelopDropDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void setClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void initView() {
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View view = layoutInflater.inflate(R.layout.dropdownlist_view, this, true);
        editText = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.btn);
        this.setOnClickListener(v -> setOnClickListener());
//        if (popupWindow != null) {
//            imageView.animate().setDuration(500).rotation(0).start();
//        }
    }

    public void setTextSize(float size) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setOnClickListener() {
        if (popupWindow == null || !popupWindow.isShowing()) {
            showPopWindow();
        } else {
            closePopWindow();
        }
    }

    /**
     * 打开下拉列表弹窗
     */
    private void showPopWindow() {
//        imageView.animate().setDuration(500).rotation(180).start();
        // 加载popupWindow的布局文件
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View contentView = layoutInflater.inflate(R.layout.dropdownlist_popupwindow, null, false);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        mDropDownListAdapter = new DropDownListAdapter(getContext(), dataList);
        listView.setAdapter(mDropDownListAdapter);
        popupWindow = new PopupWindow(contentView, this.getWidth(),
                LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        popupWindow.showAsDropDown(this);
        popupWindow.showAtLocation(contentView, Gravity.NO_GRAVITY, 0, 0);
    }

    /**
     * 关闭下拉列表弹窗
     */
    private void closePopWindow() {
//        imageView.animate().setDuration(500).rotation(0).start();
        popupWindow.dismiss();
        popupWindow = null;
        mDropDownListAdapter = null;
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setItemsData(List<String> list) {
        dataList = list;
        editText.setText(list.get(0));
    }

    public void setSelection(int position) {
        editText.setText(dataList.get(position));
        if (mOnClickListener != null) {
            mOnClickListener.setOnClickListener(position, editText.getText().toString());
        }
    }

    public void setSelection(int position, boolean isClick) {
        editText.setText(dataList.get(position));
    }

    public int getSelectedPosition() {
        return dataList.indexOf(editText.getText().toString());
    }

    public String getSelectedString() {
        return editText.getText().toString();
    }

    class DropDownListAdapter extends BaseAdapter {

        Context mContext;
        List<String> mData;
        LayoutInflater inflater;
        int mSelectedPosition;
        String mSelectedText = "";

        public DropDownListAdapter(Context ctx, List<String> data) {
            mContext = ctx;
            mData = data;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public int getSelectedPosition() {
            return mSelectedPosition;
        }

        public String getSelectedText() {
            return editText.getText().toString();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 自定义视图
            ListItemView listItemView = null;
            if (convertView == null) {
                // 获取list_item布局文件的视图
                convertView = inflater.inflate(R.layout.dropdown_list_item, null);

                listItemView = new ListItemView();
                // 获取控件对象
                listItemView.tv = (TextView) convertView
                        .findViewById(R.id.tv);

                listItemView.layout =
                        (LinearLayout) convertView.findViewById(R.id.layout_container);
                // 设置控件集到convertView
                convertView.setTag(listItemView);
            } else {
                listItemView = (ListItemView) convertView.getTag();
            }

            // 设置数据
            listItemView.tv.setText(mData.get(position));
            final String text = mData.get(position);
            listItemView.layout.setOnClickListener(v -> {
                editText.setText(text);
                mSelectedPosition = position;
                mSelectedText = text;
                closePopWindow();
                if (mOnClickListener != null) {
                    mOnClickListener.setOnClickListener(position, text);
                }
            });
            return convertView;
        }

    }

    private static class ListItemView {
        TextView tv;
        LinearLayout layout;
    }

}
