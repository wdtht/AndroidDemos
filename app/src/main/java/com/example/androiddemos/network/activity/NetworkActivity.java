package com.example.androiddemos.network.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.androiddemos.BaseActivity;
import com.example.androiddemos.R;
import com.example.androiddemos.network.fragment.OkgoFragment;
import com.example.androiddemos.network.fragment.OkhttpFragment;
import com.example.androiddemos.network.fragment.RetrofitFragment;
import com.example.androiddemos.network.fragment.SwitchNavLineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class NetworkActivity extends BaseActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    private static final String TAG = NetworkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        viewPager =  findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        initViewPagerAndBottomNavigationView(viewPager, bottomNavigationView);
    }

    /**
     * 用以启动这个activity,因为自己比别人更了解自己
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkActivity.class);
        context.startActivity(starter);
    }

    private void initViewPagerAndBottomNavigationView(ViewPager2 viewPager, BottomNavigationView bottomNavigationView) {
        List<ViewPagerData> viewPagerDataList = new ArrayList<>();
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.network_menu_action_okgo, OkgoFragment.class));
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.network_menu_action_retrofit, RetrofitFragment.class));
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.network_menu_action_okhttp, OkhttpFragment.class));
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.line_move, SwitchNavLineFragment.class));
        bindViewPagerAndBottomNavigationView(viewPager, bottomNavigationView);
        fillDataIntoViewPagerAndBottomNavigationView(viewPager, bottomNavigationView,viewPagerDataList);
    }
    private void bindViewPagerAndBottomNavigationView(ViewPager2 viewPager, BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.i(TAG, "bindViewPagerAndBottomNavigationView: item.getOrder" + item.getOrder());
            viewPager.setCurrentItem(item.getOrder());
            return true;
        });
        ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                super.onPageSelected(position);
            }
        };
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if(Lifecycle.Event.ON_DESTROY == event){
                Log.i(TAG, "NetworkActivity onDestroy bindViewPagerAndBottomNavigationView: viewPager.isActivated: " + viewPager.isActivated());
                viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
            }
        });
    }
    private void fillDataIntoViewPagerAndBottomNavigationView(ViewPager2 viewPager, BottomNavigationView bottomNavigationView, List<ViewPagerData> viewPagerDataList){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);
        for (int i = 0; i < viewPagerDataList.size(); i++) {
            ViewPagerData viewPagerData = viewPagerDataList.get(i);
            bottomNavigationView.getMenu().add(Menu.NONE,Menu.NONE,i,viewPagerData.textId).setIcon(viewPagerData.imageId);
            viewPagerAdapter.addFragment(viewPagerData.fragmentClass);
        }
    }
    
    private static class ViewPagerData{
        public int imageId;
        public int textId;
        public Class<? extends Fragment> fragmentClass;

        public ViewPagerData(int imageId, int textId, Class<? extends Fragment> fragmentClass) {
            this.imageId = imageId;
            this.textId = textId;
            this.fragmentClass = fragmentClass;
        }
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        private final List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            try {
                return fragmentClasses.get(position).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getItemCount() {
            return fragmentClasses.size();
        }

        public void addFragment(Class<? extends Fragment> clazz){
            fragmentClasses.add(clazz);
        }
    }
    
}