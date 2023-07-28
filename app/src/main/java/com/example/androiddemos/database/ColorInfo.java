package com.example.androiddemos.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 作用：颜色表信息
 *
 * @author chenkexi
 * @date :2023/7/28
 */
//Entity 可以看成是数据库中的表
@Entity(tableName = "color_info")
public class ColorInfo {
    @PrimaryKey
    @ColumnInfo(name = "change_color")
    public long changeColor;
    @ColumnInfo(name = "select_color")
    public String selectColor;
}
