package com.example.androiddemos.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
public interface UserDao {

//    OnConflictStrategy.REPLACE:如果有老的数据存在则会进行替换,如果没有就插入
//    OnConflictStrategy.ROLLBACK:如果有老的数据存在则会回滚事物,如果没有就插入
//    OnConflictStrategy.ABORT:如果有老的数据存在则会终止事物,如果没有就插入
//    OnConflictStrategy.FAIL:如果有老的数据存在则会提示插入数据失败,如果没有就插入
//    OnConflictStrategy.IGNORE:如果有老的数据存在则忽略当前数据,如果没有就插入

    //新增加用户
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo userInfo);

    /**
     * 查询当前姓名的分数
     *
     * @param name 当前名字
     * @return
     */
    @Query("SELECT score FROM user_info WHERE name = :name")
    long queryUserOfScore(String name);

    //排序，按分数从大到小排序
    @Query("SELECT * FROM user_info ORDER BY score DESC")
    List<UserInfo> getAllUserInfoByDesc();
}