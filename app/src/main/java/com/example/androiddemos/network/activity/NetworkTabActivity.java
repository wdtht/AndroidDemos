package com.example.androiddemos.network.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.androiddemos.R;
import com.example.androiddemos.databinding.ActivitySetupCenterBinding;
import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.network.adapter.SetupCenterTabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/9/23
 */
public class NetworkTabActivity extends BaseActivity {
    private final String TAG = "superdemo/NetworkTabActivity";
    ActivitySetupCenterBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "#onCreate");
        initView();
        initEvent();
    }

    private void initView() {
        Log.d(TAG, "#initView");
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_setup_center);
        SetupCenterTabAdapter adapter = new SetupCenterTabAdapter(this, this);
        mainBinding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(mainBinding.tabLayout, mainBinding.viewPager, (tab, position) -> {
            tab.setCustomView(adapter.getTabView(position));
        }).attach();
    }

    private void initEvent() {
        mainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    view.setBackgroundResource(R.drawable.selector_panel_selection_effect);  // 替换成选中背景
                    ImageView tabImageView = view.findViewById(R.id.tab_icon);
                    TextView tabTextView = view.findViewById(R.id.tab_text);
                    tabImageView.setColorFilter(getColor(R.color.color_offset_word));  // 替换成选中颜色
                    tabTextView.setTextColor(getColor(R.color.color_offset_word));  // 替换成选中颜色
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    view.setBackgroundResource(R.drawable.rounded_corner_background_item);  // 替换成未选中背景
                    ImageView tabImageView = view.findViewById(R.id.tab_icon);
                    TextView tabTextView = view.findViewById(R.id.tab_text);
                    tabImageView.setColorFilter(getColor(R.color.color_brand_50));  // 替换成未选中颜色
                    tabTextView.setTextColor(getColor(R.color.color_brand_50));  // 替换成未选中颜色
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 不需要处理
            }
        });
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkTabActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
