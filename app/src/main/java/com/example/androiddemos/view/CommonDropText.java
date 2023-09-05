package com.example.androiddemos.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androiddemos.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommonDropText extends LinearLayout {
    private DevelopDropDownView dropDownView;
    private CharSequence mLeftText = "";
    private List<String> list = new ArrayList<>();
    private TextView mTvLeft;
    private View mDivideLine;
    private boolean isShowDivideLine;
    private float mTextSize;

    public void setListener(DevelopDropDownView.onClickListener listener) {
        if (dropDownView != null) {
            dropDownView.setClickListener(listener);
            this.requestLayout();
        }
    }

    public CommonDropText(Context context) {
        this(context, null);
    }

    public CommonDropText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonDropText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DropView);
        //左边文字内容
        mLeftText = ta.getText(R.styleable.DropView_leftString);
        //右边下拉列表内容
        int resourceId = ta.getResourceId(R.styleable.DropView_rightListString, -1);
        if (resourceId != -1) {
            String[] stringArray = getResources().getStringArray(resourceId);
            list = Arrays.asList(stringArray);
        }
        //是否显示分割线
        isShowDivideLine = ta.getBoolean(R.styleable.DropView_showDivideLine, true);
        mTextSize = ta.getDimension(R.styleable.DropView_text_size, 16);
        initView();
    }

    private void initView() {
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View view = layoutInflater.inflate(R.layout.common_drop_text_view, this, true);
        mTvLeft = (TextView) findViewById(R.id.tv_content);
        mTvLeft.setText(mLeftText);
        mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        dropDownView = (DevelopDropDownView) findViewById(R.id.dropdown_menu);
        dropDownView.setTextSize(mTextSize);
        if (list != null && list.size() > 0) {
            dropDownView.setItemsData(list);
        }
        this.setOnClickListener(v -> dropDownView.setOnClickListener());

        mDivideLine = findViewById(R.id.divide_line);
        if (isShowDivideLine) {
            mDivideLine.setVisibility(VISIBLE);
        } else {
            mDivideLine.setVisibility(GONE);
        }
    }

    public void setSelection(int position) {
        dropDownView.setSelection(position);
    }

    public void setSelection(int position, boolean isClick) {
        dropDownView.setSelection(position, isClick);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setItemsData(List<String> list) {
        dropDownView.setItemsData(list);
    }

    /**
     * 设置左边文字内容
     *
     * @param text
     */
    public void setLeftText(String text) {
        mTvLeft.setText(text);
    }

    /**
     * 是否显示分割线
     *
     * @param s
     */
    public void setShowDivideLine(boolean s) {
        isShowDivideLine = s;
        if (isShowDivideLine) {
            mDivideLine.setVisibility(VISIBLE);
        } else {
            mDivideLine.setVisibility(GONE);
        }
    }

    public int getItemSelectedPosition() {
        return dropDownView.getSelectedPosition();
    }

    public String getItemSelectedString() {
        return dropDownView.getSelectedString();
    }
}
