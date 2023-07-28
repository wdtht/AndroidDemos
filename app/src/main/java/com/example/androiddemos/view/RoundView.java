package com.example.androiddemos.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.androiddemos.R;

/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/17
 */
public class RoundView extends View {

    private static final String CENTER_COLOR = "#eeff06";
    private static final int CIRCLE_RADIUS = 20;
    /**
     * 圆形半径
     */
    private int viewRadius;
    /**
     * 圆形内部填充色
     */
    private int viewColor;
    private Paint mPaint;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public RoundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundView);

        for (int i = 0; i < a.length(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.RoundView_viewRadius) {
                viewRadius = (int) a.getDimension(attr, TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, CIRCLE_RADIUS,
                        getResources().getDisplayMetrics()
                ));
            }else if (attr == R.styleable.RoundView_viewColor) {
                viewColor = a.getColor(attr, Color.parseColor(CENTER_COLOR));
            }
        }
        a.recycle();
        // 初始化画笔设置
        setPaint();
    }

    private void setPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取圆心坐标
        int cx = getWidth() / 2;
        int cy = cx;
        /**
         * 画圆心颜色
         */
        if (viewColor != 0) {
            drawCenterCircle(canvas, cx, cy);
        }
    }

    private void drawCenterCircle(Canvas canvas, int cx, int cy) {
        mPaint.setColor(viewColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, viewRadius, mPaint);
    }

    public synchronized int getViewColor() {
        return viewColor;
    }

    public synchronized void setViewColor(int color) {
        viewColor = color;
        // 进度改变时，需要通过invalidate方法进行重绘
        postInvalidate();
    }
}
