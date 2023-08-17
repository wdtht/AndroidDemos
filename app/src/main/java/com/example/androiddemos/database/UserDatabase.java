package com.example.androiddemos.database;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androiddemos.DemoApplication;

import java.io.File;
import java.util.List;

/**
 * 作用：存储颜色的数据库
 *
 * @author chenkexi
 * @date :2023/7/28
 */

@Database(entities = {UserInfo.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;//创建单例
    private static final String TAG="superdemo/UserDatabase";

    //声明这个是为了使用
    // eg:移除数据库数据
    // UserDatabase.getInstance().userDao().getAllUserInfoByDesc();
    public abstract UserDao userDao();//创建dao的抽象类

    public static UserDatabase getInstance() {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                if (instance == null) {
                    instance=buildDatabase(DemoApplication.getInstance());
                    instance.getOpenHelper().setWriteAheadLoggingEnabled(true);
//                    instance = Room.databaseBuilder(
//                                    DemoApplication.getInstance(),
//                                    UserDatabase.class, "userInfo_database.db")
//                            .fallbackToDestructiveMigration()
//                            .build();
                }
            }
        }
        return instance;
    }
    private static UserDatabase buildDatabase(final Context appContext) {
//        File file = new File(getConfigFolder());
//        if(!file.exists()){
//            file.mkdir();
//        }
//        String dbPath = getConfigFolder() + File.separator + "userInfo_database.db";
        RoomDatabase.Builder<UserDatabase> builder = Room.databaseBuilder(appContext, UserDatabase.class, "userInfo_database.db");
        builder.addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d(TAG, "room onCreate");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.d(TAG, "room onOpen");
            }
        });
        return builder.build();
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    public static String getAppDataFolder() {
        return getSDCardPath() + File.separator+"SUPER/";
    }
    public static String getConfigFolder() {
        return getAppDataFolder() + File.separator + "Config";
    }
}
