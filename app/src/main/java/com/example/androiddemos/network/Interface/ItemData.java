package com.example.androiddemos.network.Interface;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/9/21
 */
// ItemData.java

public class ItemData {
    public String title;
    public String content;
    public boolean isStarred;
    public boolean isSelected = false;

    public ItemData(String title, String content, boolean isStarred) {
        this.title = title;
        this.content = content;
        this.isStarred = isStarred;//当前导航线显示星星
    }
}

