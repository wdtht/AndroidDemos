package com.example.androiddemos.view;

import android.app.Activity;

import androidx.annotation.NonNull;
import com.example.androiddemos.R;
import com.github.gzuliyujiang.wheelpicker.OptionPicker;

/**
 * 作用:底部弹窗picker选中器
 *
 * @author chenkexi
 * @date :2023/9/26
 */
public class PickerDialog extends OptionPicker {
    public PickerDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected void initData() {
        super.initData();
        //设置上面的取消，确认view
        headerView.setBackgroundColor(getContext().getColor(R.color.color_side_draw_the_base));
        okView.setText("Confirm");
        okView.setTextColor(getContext().getColor(R.color.color_brand));
        okView.setTextSize(18);
        cancelView.setText("Cancel");
        cancelView.setTextColor(getContext().getColor(R.color.color_first_level_word));
        cancelView.setTextSize(18);
        //设置picker
        wheelLayout.setVisibleItemCount(4);
        wheelLayout.setCyclicEnabled(false);
        wheelLayout.setTextColor(getContext().getColor(R.color.color_three_level_word));
        wheelLayout.setSelectedTextColor(getContext().getColor(R.color.color_brand));
        wheelLayout.setDefaultPosition(1);
    }

}
