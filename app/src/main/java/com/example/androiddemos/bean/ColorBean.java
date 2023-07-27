package com.example.androiddemos.bean;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/27
 */
public class ColorBean {
    private String _selectColor;
    private String _changeColor;


    public String selectColor() {
        return _selectColor;
    }

    public void selectColor(String color) {
        this._selectColor = color;
    }

    public String changeColor() {
        return _changeColor;
    }

    public void changeColor(String color) {
        this._changeColor = color;
    }
}
