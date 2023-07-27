package com.example.androiddemos.jetpack.model;

import com.example.androiddemos.bean.ColorBean;

import de.greenrobot.event.EventBus;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/27
 */
public class JetpackModel extends BaseModel{
    private EventBus BUS = EventBus.getDefault();
    public void init(){
        ColorBean colorBean = new ColorBean();
        colorBean.changeColor("#777777");
        colorBean.selectColor("#777777");
        BUS.post(colorBean);
    }
}
