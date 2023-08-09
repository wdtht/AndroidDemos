package com.example.androiddemos.view;

public interface OnLeftOrRightButtonClick {
    /**
     * 右侧按钮点击事件回调
     *
     * @param str
     */
    void onRightClick(String str);

    void onLeftClick(String str);

    void onEditText(String str);
}
