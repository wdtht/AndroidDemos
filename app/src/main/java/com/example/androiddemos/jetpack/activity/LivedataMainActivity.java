package com.example.androiddemos.jetpack.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.jetpack.model.LivedataModel;
import com.example.androiddemos.jetpack.viewmodel.LivedataViewModel;
import com.example.androiddemos.view.RoundView;
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
public class LivedataMainActivity extends BaseActivity implements View.OnClickListener {
    private LivedataViewModel livedataViewModel;
    private final String TAG = "superdemo/JetpackMainActivity";
    private Button controlButton;
    private RoundView selectView;//下面的圆
    private RoundView changeView;//上面的圆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata_main);
        selectView = (RoundView)findViewById(R.id.select_color);
        changeView = (RoundView)findViewById(R.id.change_color);
        controlButton =(Button) findViewById(R.id.control_btn);
        changeView.setOnClickListener(this);
        controlButton.setOnClickListener(this);
        LivedataModel livedataModel = new LivedataModel();
        initViewModel();
    }

    private void initViewModel() {

        livedataViewModel = new ViewModelProvider(this).get(LivedataViewModel.class);
        livedataViewModel.getChangeColor().observe(this, (String changeColor) -> {
            Log.d(TAG, "changeColor: " + changeColor);
            changeView.setViewColor(Color.parseColor(changeColor));
        });
        livedataViewModel.getSelectColor().observe(this, selectColor -> {
            Log.d(TAG, "selectColor:" + selectColor);
            selectView.setViewColor(Color.parseColor(selectColor));
        });
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
    public void onClick(View v) {
        if(v.getId() == R.id.change_color){
            Log.d(TAG, "onClick change_color");
            //setValue()要在主线程中调用，而postValue()既可在主线程也可在子线程中调用
            livedataViewModel.getChangeColor().setValue("#989877");
        } else if (v.getId() == R.id.control_btn) {
            Log.d(TAG, "onClick control_btn");
            livedataViewModel.getSelectColor().setValue("#989877");
        }
    }
}