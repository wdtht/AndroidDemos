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

@Database(entities = {UserInfo.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;//创建单例

    //声明这个是为了使用
    // eg:移除数据库数据
    // UserDatabase.getInstance().userDao().getAllUserInfoByDesc();
    public abstract UserDao userDao();//创建dao的抽象类

    static UserDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    UserDatabase.class, "userInfo_database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
