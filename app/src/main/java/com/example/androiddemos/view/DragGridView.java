package com.example.androiddemos.view;

import static com.example.basepop.basepop.base.utils.PxTool.getStatusHeight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class DragGridView extends GridView {

    private static final String TAG = "DragGridView";

    private DragController mDragController;
    private int mDownX = -1;
    private int mDownY = -1;

    public DragGridView(Context context) {
        super(context);
        init(context);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDragController = new DragController(context);
        setOnTouchListener(mDragController);
        setOnItemLongClickListener(mDragController);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = (int) ev.getX();
                    mDownY = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(ev.getX() - mDownX) > mDragController.getScaledTouchSlop() ||
                            Math.abs(ev.getY() - mDownY) > mDragController.getScaledTouchSlop()) {
                        return false;
                    }
                    break;
            }
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onInterceptTouchEvent", e);
            return false;
        }
    }

    public void setDragAdapter(DragAdapter adapter) {
        setAdapter(adapter);
        mDragController.setDragAdapter(adapter);
    }
}

class DragController implements View.OnTouchListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "DragController";

    private DragGridView mDragGridView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private View mDragView;
    private int mDownRawX;
    private int mDownRawY;

    // Scroll boundaries
    private static final int SCROLL_BOUNDARY = 100;
    private static final int SCROLL_AMOUNT = 30;
    private int mDragPosition;
    private int mPoint2ItemTop;
    private int mPoint2ItemLeft;
    private int mOffset2Top;
    private int mOffset2Left;
    private int mStatusHeight;
    private int mScaledTouchSlop;
    private DragAdapter mDragAdapter;
    private boolean mIsDrag;

    public DragController(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setDragAdapter(DragAdapter dragAdapter) {
        mDragAdapter = dragAdapter;
    }

    public int getScaledTouchSlop() {
        return mScaledTouchSlop;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            if (!mIsDrag && mDragGridView.getScrollY() == 0 && event.getAction() == MotionEvent.ACTION_MOVE) {
                scrollAsNeeded((int) event.getRawY());
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Exception in onTouch", e);
            return false;
        }
    }

    private void scrollAsNeeded(int rawY) {
        int scrollY;
        if (rawY < SCROLL_BOUNDARY) {
            scrollY = -SCROLL_AMOUNT;
        } else if (rawY > mDragGridView.getHeight() - SCROLL_BOUNDARY) {
            scrollY = SCROLL_AMOUNT;
        } else {
            scrollY = 0;
        }

        // Scroll the GridView
        try {
            mDragGridView.smoothScrollBy(scrollY, SCROLL_AMOUNT);
        } catch (Exception e) {
            Log.e(TAG, "Exception in scrollAsNeeded", e);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (position != AdapterView.INVALID_POSITION) {
            mDragGridView = (DragGridView) parent;
            int mDownX = (int) view.getX();
            int mDownY = (int) view.getY();
            mDragPosition = position;
            mPoint2ItemTop = mDownY - view.getTop();
            mPoint2ItemLeft = mDownX - view.getLeft();
            mOffset2Top = (int) (view.getY() - mDownY);
            mOffset2Left = (int) (view.getX() - mDownX);
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);
            Bitmap dragBitmap = Bitmap.createBitmap(view.getDrawingCache());
            startDrag(dragBitmap, (int) view.getY());
            mDragAdapter.setHideItem(mDragPosition);
            mDragGridView.invalidate();
            mIsDrag = true;
            return true;
        }
        return false;
    }

    private void startDrag(Bitmap dragBitmap, int rawY) {
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowParams.x = mDownRawX - mPoint2ItemLeft + mOffset2Left;
        mWindowParams.y = rawY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.windowAnimations = 0;
        ImageView imageView = new ImageView(mDragGridView.getContext());
        imageView.setImageBitmap(dragBitmap);
        mWindowManager.addView(imageView, mWindowParams);
        mDragView = imageView;
    }

    // More code for the DragController here, handling drag events, movement and item swapping
}
