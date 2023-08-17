package com.example.androiddemos.jetpack.config;

import android.util.Log;

import com.example.androiddemos.constants.ConstKey;
import com.example.androiddemos.database.UserInfo;
import com.example.androiddemos.database.UserRepository;
import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.util.List;

/**
 * 作用：游戏用户配置文件
 *
 * @author chenkexi
 * @date :2023/8/16
 */
public class GameUserConfig {
    private static String Tag = "superdemo/GameUserConfig";
    /**
     * 用户信息
     */
    private UserInfo user =new UserInfo();
    private List<UserInfo> allUser;
    private static GameUserConfig instance = null;

    public static GameUserConfig getInstance() {
        synchronized (GameUserConfig.class) {
            if (instance == null) {
                UserRepository.Companion.getInstance();
                instance = new GameUserConfig();
            }
            return instance;
        }
    }


    public Long setConfig(UserInfo userInfo) {
        Log.d(Tag, "#setConfig");
        this.user = userInfo;
        return saveConfig();
    }

    private Long saveConfig() {
        Log.d(Tag, "#saveConfig");
        return UserRepository.Companion.getInstance().inserUser(this.user);
    }

    public void UpdataConfig(UserInfo userInfo) {
        Log.d(Tag, "#saveConfig");
        UserRepository.Companion.getInstance().updateUser(userInfo);
    }

    //初始需要读一下配置文件

    public void readConfig() {
        Log.d(Tag, "#readConfig");
        new Thread(() -> {
            this.allUser = UserRepository.Companion.getInstance().getAllUser();
            Long currentUserId = MMKV.defaultMMKV().decodeLong(ConstKey.CURRENT_USER_KEY);
            Log.d(Tag, "currentUser:" + currentUserId);
            if (currentUserId != 0L) {
                this.user = UserRepository.Companion.getInstance().getOneUserById(currentUserId);
            }
        }).start();
    }

    public UserInfo getUser() {
        Log.d(Tag, "#getUser user:"+ new Gson().toJson(user));
        return user;
    }

    public List<UserInfo> getAllUser() {
        return allUser;
    }

}
