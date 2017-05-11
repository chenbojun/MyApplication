package com.example.bojunchen.myapplication.ui.ui.viewdraghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.ui.common.BaseActivity;
import com.example.bojunchen.myapplication.ui.ui.common.SwipeLayout;

/**
 * Created by bojunchen on 2017/3/21.
 */

public class TestViewDragHelperActivity extends BaseActivity{

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TestViewDragHelperActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_drag_helper);
        init();
    }

    private void init() {
        SwipeLayout swipeLayout = (SwipeLayout) findViewById(R.id.list_item_view);
        swipeLayout.setOnSwipeStatusListener(new SwipeLayout.OnSwipeStatusListener() {
            @Override
            public void onStatusChanged(SwipeLayout.Status status) {

            }

            @Override
            public void onStartCloseAnimation() {

            }

            @Override
            public void onStartOpenAnimation() {

            }

        });
    }

}
