package com.example.androiddemos.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/**
 * 作用：存储颜色的数据库
 *
 * @author chenkexi
 * @date :2023/7/28
 */

@Database(entities = {ColorInfo.class}, version = 2, exportSchema = false)
public abstract class ColorDatabase extends RoomDatabase{
    private static ColorDatabase instance;//创建单例
    public abstract ColorDao colorDao();//创建dao的抽象类

    static ColorDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (ColorDatabase.class) {
                if (instance == null)    {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ColorDatabase.class, "color_database")
                            .addCallback(sOnOpenCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;}

    private static RoomDatabase.Callback sOnOpenCallback =
            new RoomDatabase.Callback(){
    };
}
