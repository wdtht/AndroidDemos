package com.example.androiddemos.database;

import androidx.room.RoomDatabase;

import java.util.List;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/28
 */
public final class ColorDao_Impl implements ColorDao{
    private final RoomDatabase __db;

    public ColorDao_Impl(RoomDatabase db) {
        __db = db;

    }

    @Override
    public void updateChangeColor(ColorInfo changeColor) {

    }

    @Override
    public void updateSelectColor(ColorInfo selectColor) {

    }

    @Override
    public String getChangeColor() {
        return null;
    }

    @Override
    public String getSelectColor() {
        return null;
    }
}
