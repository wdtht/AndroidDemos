package com.example.androiddemos.network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androiddemos.R;
import com.example.androiddemos.network.fragment.OkgoFragment;
import com.example.androiddemos.network.fragment.RetrofitFragment;
import com.example.androiddemos.network.fragment.SwitchNavLineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/9/23
 */
public class SetupCenterTabAdapter extends FragmentStateAdapter {

    private List<ViewPagerData> viewPagerDataList = new ArrayList<>();

    private Context mContext;

    public SetupCenterTabAdapter(@NonNull FragmentActivity fragmentActivity, Context context) {
        super(fragmentActivity);
        mContext = context;
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.network_menu_action_okgo));
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.network_menu_action_retrofit));
        viewPagerDataList.add(new ViewPagerData(R.drawable.finish, R.string.line_move));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SwitchNavLineFragment();
            case 1:
                return new RetrofitFragment();
            case 2:
                return new OkgoFragment();
            default:
                return new SwitchNavLineFragment();
        }
    }

    @Override
    public int getItemCount() {
        return viewPagerDataList.size();
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView tabTextView = view.findViewById(R.id.tab_text);
        ImageView tabImageView = view.findViewById(R.id.tab_icon);
        // 在这里设置初始颜色和图标
        tabTextView.setText(viewPagerDataList.get(position).textId);
        tabImageView.setImageResource(viewPagerDataList.get(position).imageId);

        return view;
    }

    private static class ViewPagerData {
        public int imageId;
        public int textId;

        public ViewPagerData(int imageId, int textId) {
            this.imageId = imageId;
            this.textId = textId;
        }
    }
}
