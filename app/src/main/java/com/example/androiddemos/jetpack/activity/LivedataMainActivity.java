package com.example.androiddemos.jetpack.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.DemoApplication;
import com.example.androiddemos.R;
import com.example.androiddemos.constants.ConstKey;
import com.example.androiddemos.database.UserInfo;
import com.example.androiddemos.databinding.ActivityLivedataMainBinding;
import com.example.androiddemos.jetpack.config.GameUserConfig;
import com.example.androiddemos.jetpack.viewmodel.LivedataViewModel;
import com.example.androiddemos.view.EditPop;
import com.example.androiddemos.view.OnLeftOrRightButtonClick;
import com.tencent.mmkv.MMKV;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏设计：1.一进来有一个弹窗需要输入名字，点击确认，页面显示当前玩游戏的人是谁，游戏开始，可以选上面的圆切换颜色，点击开始，下面的圆颜色会变，当变
 * 的时候点击停止，若上下两个圆一样就可以继续玩，当出现无法上下一致的时候，一局结束，记录连续选中次数，弹框游戏结束，共连续多少次，下一位玩家谁：输入
 * 名字，以此反复。有排行榜可以看自己的名字排行在多少，仅显示前10
 * 数据库设计：每一列元素：姓名，连续次数；数据库每次插入后需要排序（按连续次数排，最多的在最前面）
 * 颜色存储：键值对，用Mmkv存储
 *
 * @author chenkexi
 * @date :2023/7/27
 */
public class LivedataMainActivity extends BaseActivity {
    private LivedataViewModel livedataViewModel;
    private final String TAG = "superdemo/JetpackMainActivity";
    private EditPop editPop;
    private UserInfo userInfo;
    ActivityLivedataMainBinding mainBinding;
    private Integer changeColorIndex = 0;
    private Integer selectColorIndex = 0;
    private Boolean isStop = false;
    private String[] colorAll;
    private Timer timer;

    private static final int MIN_CLICK_DELAY_TIME= 1000;
    private static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "#onCreate");
        initView();
        initEvent();
    }

    private void initView() {
        Log.d(TAG, "#initView");
        //初始先从数据库读当前玩家信息
        GameUserConfig.getInstance().readConfig();
        changeColorIndex = MMKV.defaultMMKV().decodeInt(ConstKey.SELECT_COLOR_KEY);
        colorAll = ConstKey.COLOR_ALL;
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_livedata_main);
        mainBinding.changeColor.setViewColor(Color.parseColor(colorAll[changeColorIndex]));
        editPop = new EditPop(this, onLeftOrRightButtonClick());
        editPop.setCanceledOnTouchOutside(false);
        editPop.show();
        userInfo = GameUserConfig.getInstance().getUser();
        if (userInfo.name == null) {
            mainBinding.nowPlayerName.setText("当前玩家是：");
            mainBinding.nowPlayerScore.setText("");
        } else {
            mainBinding.nowPlayerName.setText("当前玩家是：" + userInfo.name);
            mainBinding.nowPlayerScore.setText(String.valueOf(userInfo.score));
        }
//        LivedataModel livedataModel = new LivedataModel();
//        initViewModel();
    }

    void initEvent() {
        Log.d(TAG, "#initEvent");
        mainBinding.changeColor.setOnClickListener(v -> {
            changeColorIndex = (changeColorIndex + 1) % colorAll.length;
            Log.d(TAG, "#changeColor click now changeColor: " + colorAll[changeColorIndex]);
            mainBinding.changeColor.setViewColor(Color.parseColor(colorAll[changeColorIndex]));
        });
        mainBinding.controlBtn.setOnClickListener(v -> {
            controlBtnClick();
        });
    }

    private void controlBtnClick() {
        //防多次恶意点击
        long curClickTime = System.currentTimeMillis();
        if (Math.abs(curClickTime - lastClickTime) < MIN_CLICK_DELAY_TIME) {
            Log.d(TAG, "controlBtn click too fast!");
            lastClickTime = curClickTime;
            return;
        }
        lastClickTime = curClickTime;
        Log.d(TAG, "controlBtn click!");
        isStop = !isStop;
        if (isStop) {
            mainBinding.controlBtn.setText("停止");
            changeColor();
        } else {
            timer.cancel();
            mainBinding.controlBtn.setText("开始");
            if (selectColorIndex == changeColorIndex) {
                Toast.makeText(this, "恭喜挑战成功！！！", Toast.LENGTH_LONG).show();
                userInfo.score++;
                mainBinding.nowPlayerScore.setText(String.valueOf (userInfo.score));
            } else {
                Toast.makeText(this, "很抱歉挑战失败！！！", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void changeColor() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                selectColorIndex = random.nextInt(colorAll.length);
                mainBinding.selectColor.setViewColor(Color.parseColor(colorAll[selectColorIndex]));
            }
        }, 0, 500);
    }


    private OnLeftOrRightButtonClick onLeftOrRightButtonClick() {
        return new OnLeftOrRightButtonClick() {
            @Override
            public void onRightClick(String str) {
                Log.d(TAG, "onRightClick: " + str);
                runOnUiThread(() -> {
                    Toast.makeText(LivedataMainActivity.this, str, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onLeftClick(String str) {

            }

            @Override
            public void onEditText(String str) {
                Log.d(TAG, "onEditText: " + str);
                mainBinding.nowPlayerName.setText("当前玩家是：" + str);
                userInfo.name = str;
                userInfo.score = 0;
                //用线程池存储
                DemoApplication.getInstance().getExecutor().execute(() -> {
                    Long id = GameUserConfig.getInstance().setConfig(userInfo);
                    Log.d(TAG, "id:" + id);
                    //将当前玩家存储起来
                    MMKV.defaultMMKV().encode(ConstKey.CURRENT_USER_KEY, id);
                });
            }
        };
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, LivedataMainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy name:" + userInfo.name + " score:" + userInfo.score + " id:" + userInfo._id);
        //存上面圆的颜色
        MMKV.defaultMMKV().encode(ConstKey.SELECT_COLOR_KEY, changeColorIndex);
        new Thread(() -> {
            GameUserConfig.getInstance().UpdataConfig(userInfo);
        }).start();
        editPop.dismiss();
    }

    //    public void onClick(View v) {
//        if(v.getId() == R.id.change_color){
//            Log.d(TAG, "onClick change_color");
//            //setValue()要在主线程中调用，而postValue()既可在主线程也可在子线程中调用
//            livedataViewModel.getChangeColor().setValue("#989877");
//        } else if (v.getId() == R.id.control_btn) {
//            Log.d(TAG, "onClick control_btn");
//            livedataViewModel.getSelectColor().setValue("#989877");
//        }
//    }

//    private void initViewModel() {
//        Log.d(TAG, "#initViewModel");
//        livedataViewModel = new ViewModelProvider(this).get(LivedataViewModel.class);
//        livedataViewModel.getChangeColor().observe(this, (String changeColor) -> {
//            Log.d(TAG, "changeColor: " + changeColor);
//            mainBinding.changeColor.setViewColor(Color.parseColor(changeColor));
//        });
//        livedataViewModel.getSelectColor().observe(this, selectColor -> {
//            Log.d(TAG, "selectColor:" + selectColor);
//            mainBinding.selectColor.setViewColor(Color.parseColor(selectColor));
//        });
//    }

}