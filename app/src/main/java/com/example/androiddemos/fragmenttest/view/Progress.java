package com.example.androiddemos.fragmenttest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.androiddemos.R;


/**
 * 作用：
 *
 * @author chenkexi
 * @date :2023/7/17
 */
public class Progress extends View {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius;
    private final int backColor;
    private final int barColor;
    private final int textColor;

    int progress = 0;

    public Progress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // R.styleable来自与values/attrs.xml中的styleable定义
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Progress);
        backColor = ta.getColor(R.styleable.Progress_backColor,0x000000);
        barColor = ta.getColor(R.styleable.Progress_barColor, 0x000000);
        textColor=ta.getColor(R.styleable.Progress_textColor,0x000000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ta.close();
        }else{
            ta.recycle();
        }
    }

    public Progress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Progress(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = (float) this.getMeasuredHeight() / 5;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景
        mPaint.setColor(backColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight()), radius, radius, mPaint);
        //进度条
        mPaint.setColor(barColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, this.getMeasuredWidth() * progress / 100f, this.getMeasuredHeight()), radius, radius, mPaint);
        //进度
        mPaint.setColor(textColor);
        mPaint.setTextSize(this.getMeasuredHeight() / 1.2f);
        String text = "" + progress + "%";
        float x = ((float) this.getMeasuredWidth() - mPaint.measureText(text))/2;
        float y = this.getMeasuredHeight() / 2f - mPaint.getFontMetrics().ascent / 2f - mPaint.getFontMetrics().descent / 2f;
//        float x = ((float) this.getMeasuredWidth()) * progress / 100 - mPaint.measureText(text) - 10;
        canvas.drawText(text, x, y, mPaint);
    }

    /*设置进度条进度, 外部调用*/
    public void setProgress(int progress) {
        if (progress > 100) {
            this.progress = 100;
        } else this.progress = Math.max(progress, 0);
        postInvalidate();
    }
}
