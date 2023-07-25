
package com.example.androiddemos.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
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
public class RoundProgressBar extends View {

    private static final int START_ANGLE = -90;
    private static final String CENTER_COLOR = "#eeff06";
    private static final String RING_COLOR = "#FF7281E1";
    private static final String PROGRESS_COLOR = "#FFDA0F0F";
    private static final String TEXT_COLOR = "#FF000000";
    private static final int TEXT_SIZE = 30;
    private static final int CIRCLE_RADIUS = 20;
    private static final int RING_WIDTH = 5;
    /**
     * 圆弧的起始角度，参考canvas.drawArc方法
     */
    private int startAngle;
    /**
     * 圆形内半径
     */
    private int radius;
    /**
     * 进度条的宽度
     */
    private int ringWidth;
    /**
     * 默认进度
     */
    private int mProgress = 0;
    /**
     * 圆形内部填充色
     */
    private int centerColor;
    /**
     * 进度条背景色
     */
    private int ringColor;
    /**
     * 进度条的颜色
     */
    private int progressColor;
    /**
     * 文字大小
     */
    private int textSize;
    /**
     * 文字颜色
     */
    private int textColor;
    /**
     * 文字是否需要显示
     */
    private boolean isTextDisplay;
    private String textContent;
    private Paint mPaint;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        for (int i = 0; i < a.length(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.RoundProgressBar_startAngle) {
                startAngle = a.getInteger(attr, START_ANGLE);
            } else if (attr == R.styleable.RoundProgressBar_centerColor) {
                centerColor = a.getColor(attr, Color.parseColor(CENTER_COLOR));
            } else if (attr == R.styleable.RoundProgressBar_progressColor) {
                progressColor = a.getColor(attr, Color.parseColor(PROGRESS_COLOR));
            } else if (attr == R.styleable.RoundProgressBar_ringColor) {
                ringColor = a.getColor(attr, Color.parseColor(RING_COLOR));
            } else if (attr == R.styleable.RoundProgressBar_roundTextColor) {
                textColor = a.getColor(attr, Color.parseColor(TEXT_COLOR));
            } else if (attr == R.styleable.RoundProgressBar_textSize) {
                textSize = (int) a.getDimension(attr, TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE,
                        getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.RoundProgressBar_isTextDisplay) {
                isTextDisplay = a.getBoolean(attr, true);
            } else if (attr == R.styleable.RoundProgressBar_radius) {
                radius = (int) a.getDimension(attr, TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, CIRCLE_RADIUS,
                        getResources().getDisplayMetrics()
                ));
            } else if (attr == R.styleable.RoundProgressBar_ringWidth) {
                ringWidth = (int) a.getDimension(attr, TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, RING_WIDTH,
                        getResources().getDisplayMetrics()
                ));
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
        if (centerColor != 0) {
            drawCenterCircle(canvas, cx, cy);
        }
        /**
         * 画外层大圆
         */
        drawOuterCircle(canvas, cx, cy);
        /**
         * 画进度圆弧
         */
        drawProgress(canvas, cx, cy);
        /**
         * 画出进度百分比
         */
        drawProgressText(canvas, cx, cy);
    }

    private void drawProgressText(Canvas canvas, int cx, int cy) {
        if (!isTextDisplay) {
            return;
        }
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setStrokeWidth(0);
        textContent = getProgress() + "%";
        float textWidth = mPaint.measureText(textContent);
        canvas.drawText(textContent, cx - textWidth / 2, cy + textSize / 2, mPaint);
    }

    private void drawProgress(Canvas canvas, int cx, int cy) {
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF mRectF = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        float sweepAngle = (float) (mProgress * 360.0 / 100);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);
    }

    private void drawOuterCircle(Canvas canvas, int cx, int cy) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ringColor);
        mPaint.setStrokeWidth(ringWidth);
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    private void drawCenterCircle(Canvas canvas, int cx, int cy) {
        mPaint.setColor(centerColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    public synchronized int getProgress() {
        return mProgress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        mProgress = progress;
        // 进度改变时，需要通过invalidate方法进行重绘
        postInvalidate();
    }
}
