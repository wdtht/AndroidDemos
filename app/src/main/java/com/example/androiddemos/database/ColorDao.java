package com.example.androiddemos.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/28
 */
//DAO 提供访问数据库的方法
@Dao
public interface ColorDao {
    @Update
    public void updateChangeColor(ColorInfo  changeColor);

    @Update
    public void updateSelectColor(ColorInfo  selectColor);

    @Query("SELECT change_color FROM color_info")
    public String getChangeColor();

    @Query("SELECT select_color FROM color_info")
    public String getSelectColor();
}
