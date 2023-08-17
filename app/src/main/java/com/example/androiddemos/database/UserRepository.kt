package com.example.androiddemos.database

import android.util.Log

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/8/16
 */
class UserRepository private constructor(val userDatabase: UserDatabase) {
    companion object {
        private const val TAG = "superdemo/UserRepository"
        val instance = UserRepository(UserDatabase.getInstance())
//        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//            UserRepository(UserDatabase.getInstance())
//        }
    }
    fun inserUser(userInfo:UserInfo):Long{
        Log.d(TAG,"#inserUser name:"+userInfo.name)
        return userDatabase.userDao().insertUser(userInfo)
    }

    fun getAllUser():List<UserInfo>{
        Log.d(TAG,"#getAllUser")
        return userDatabase.userDao().allUserInfoByDesc
    }

    fun deleteUser(userInfo:UserInfo){
        Log.d(TAG,"#deleteUser name:"+userInfo.name)
        userDatabase.userDao().deleteUser(userInfo._id)
    }

    fun getScoreByUserId(userInfo:UserInfo){
        Log.d(TAG,"#getScoreByUserId name:"+userInfo.name)
        userDatabase.userDao().getScoreByUserId(userInfo._id)
    }

    fun updateUser(userInfo:UserInfo){
        Log.d(TAG,"#updateUser name:"+userInfo.name +" score:"+userInfo.score)
        userDatabase.userDao().updateUserInfoById(userInfo._id,userInfo.score)
    }
    fun getOneUserById(id:Long):UserInfo{
        Log.d(TAG, "#getOneUserById id:$id")
        return userDatabase.userDao().getOneUserById(id)
    }

}