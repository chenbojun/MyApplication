package com.example.bojunchen.myapplication.ui.ui.materialdesign.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * Created by bojunchen on 2017/5/4.
 */

public class ScrollToTopBehavior extends CoordinatorLayout.Behavior<View>{
    int offsetTotal = 0;
    boolean scrolling = false;

    public ScrollToTopBehavior() {

    }

    public ScrollToTopBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset(child, dyConsumed);
    }

    public void offset(View child,int dy){
        /**
         * 计算顶部控件应该滑动的距离delta
         */
        int old = offsetTotal;
        int top = offsetTotal - dy;
        top = Math.max(top, -child.getHeight());
        top = Math.min(top, 0);
        offsetTotal = top;
        if (old == offsetTotal){
            scrolling = false;
            return;
        }
        int delta = offsetTotal-old;

        // 调整顶部控件位置
        child.offsetTopAndBottom(delta);
        scrolling = true;
    }

}
