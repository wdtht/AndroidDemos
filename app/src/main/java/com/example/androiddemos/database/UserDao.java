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
    Long insertUser(UserInfo userInfo);

    /**
     * 查询当前用户的分数
     *
     * @param id 当前名字
     * @return
     */
    @Query("SELECT score FROM user_info WHERE _id = :id")
    long getScoreByUserId(Long id);

    /**
     * 查询某个id对应的用户信息
     *
     * @param id 当前用户id
     * @return
     */
    @Query("SELECT * FROM user_info WHERE _id = :id")
    UserInfo getOneUserById(Long id);

    /**
     * 删除某一个用户
     *
     * @return
     */
    @Query("DELETE FROM user_info WHERE _id == (:id)")
    void deleteUser(Long id);

    //排序，按分数从大到小排序
    @Query("SELECT * FROM user_info ORDER BY score DESC")
    List<UserInfo> getAllUserInfoByDesc();

    @Query("UPDATE user_info SET score =(:score) WHERE _id == (:id)")
    void updateUserInfoById(Long id,Long score);
}