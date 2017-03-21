package com.example.bojunchen.myapplication.ui.ui.common;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 支持侧滑菜单Item
 *
 * <p>使用方法：保持SwipeLayout中仅包含2个childView，第1个为内容区域，第2个为菜单区域</p>
 *
 * Created by bojunchen on 2017/3/21.
 */

public class SwipeLayout extends FrameLayout{

    private static final String LOG_TAG = "SwipeLayout";

    ViewDragHelper viewDragHelper;

    Status status = Status.CLOSE;

    boolean smooth = true;

    OnSwipeStatusListener mOnSwipeStatusListener;

    View itemView;
    View menu;

    int itemWidth;
    int itemHeight;
    int menuWidth;
    int menuHeight;

    public enum Status {
        OPEN,
        CLOSE
    }

    public SwipeLayout(@NonNull Context context) {
        super(context);
        init(null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        viewDragHelper = ViewDragHelper.create(this, (float) 1.0, mCallback);
    }

    public void setOnSwipeStatusListener(OnSwipeStatusListener onSwipeStatusListener) {
        this.mOnSwipeStatusListener = onSwipeStatusListener;
    }

    public void setStatus(Status status, boolean smooth) {
        this.status = status;
        if (Status.OPEN.equals(status)) {
            open(smooth);
        } else {
            close(smooth);
        }
    }

    /**
     * 打开菜单
     *
     * @param smooth
     */
    public void open(boolean smooth) {
        Status preStatus = status;
        status = Status.OPEN;
        if (smooth) {
            if (viewDragHelper.smoothSlideViewTo(itemView, -menuWidth, 0)) {
                if (mOnSwipeStatusListener != null) {
                    mOnSwipeStatusListener.onStartOpenAnimation();
                    Log.d(LOG_TAG, "mOnSwipeStatusListener.onStartOpenAnimation()");
                }
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layout(status);
        }

        if (mOnSwipeStatusListener != null && preStatus.equals(Status.CLOSE)) {
            mOnSwipeStatusListener.onStatusChanged(status);
            Log.d(LOG_TAG, "mOnSwipeStatusListener.onStatusChanged() " + "status == " + status);
        }
    }

    /**
     * 关闭菜单
     *
     * @param smooth
     */
    public void close(boolean smooth) {
        Status preStatus = status;
        status = Status.CLOSE;
        if (smooth) {
            if (viewDragHelper.smoothSlideViewTo(itemView, 0, 0)) {
                if (mOnSwipeStatusListener != null) {
                    mOnSwipeStatusListener.onStartCloseAnimation();
                    Log.d(LOG_TAG, "mOnSwipeStatusListener.onStartCloseAnimation()");
                }
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layout(status);
        }
        if (mOnSwipeStatusListener != null && preStatus.equals(Status.OPEN)) {
            mOnSwipeStatusListener.onStatusChanged(status);
            Log.d(LOG_TAG, "mOnSwipeStatusListener.onStatusChanged() " + "status == " + status);
        }
    }

    private void layout(Status status) {
        if (Status.OPEN.equals(status)) {
            menu.layout(itemWidth - menuWidth, 0, itemWidth, itemHeight);
            itemView.layout(-menuWidth, 0, itemWidth - menuWidth, itemHeight);
        } else {
            menu.layout(itemWidth, 0, itemWidth + menuWidth, itemHeight);
            itemView.layout(0, 0, itemWidth, itemHeight);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        itemView = getChildAt(1);
        menu = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = itemView.getMeasuredWidth();
        itemHeight = itemView.getMeasuredHeight();
        menuWidth = menu.getMeasuredWidth();
        menuHeight = menu.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layout(Status.CLOSE);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return itemView == child;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (itemView == changedView) {
                menu.offsetLeftAndRight(dx);
            }
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (itemView == releasedChild) {
                if (xvel == 0 && Math.abs(itemView.getLeft()) > menuWidth / 2.0f) {
                    open(smooth);
                } else if (xvel < 0) {
                    open(smooth);
                } else {
                    close(smooth);
                }
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return menuWidth;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (itemView == child) {
                if (left > 0) {
                    return 0;
                } else {
                    return Math.max(left, -menuWidth);
                }
            }
            return 0;
        }

    };

    public interface OnSwipeStatusListener {

        /**
         * 当状态改变时回调
         *
         * @param status 菜单开启状态
         */
        void onStatusChanged(Status status);

        /**
         * 开始执行Open动画
         */
        void onStartCloseAnimation();

        /**
         * 开始执行Close动画
         */
        void onStartOpenAnimation();

    }

}
