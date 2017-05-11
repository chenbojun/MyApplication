package com.example.bojunchen.myapplication.ui.ui.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.ui.common.BaseActivity;
import com.example.bojunchen.myapplication.ui.ui.materialdesign.MaterialActivity1;
import com.example.bojunchen.myapplication.ui.ui.materialdesign.MaterialActivity2;
import com.example.bojunchen.myapplication.ui.ui.recycleview.SecondActivity;
import com.example.bojunchen.myapplication.ui.ui.rn.MyReactActivity;
import com.example.bojunchen.myapplication.ui.ui.viewdraghelper.TestViewDragHelperActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setExitTransition(new Explode());
        }
        initListener();
        handleStatusBar();

    }

    private void initListener() {
        findViewById(R.id.tv_hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondActivity.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.tv_view_drag_helper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestViewDragHelperActivity.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.tv_material_study_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialActivity1.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.tv_material_study_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialActivity2.startActivity(MainActivity.this);
            }
        });

        findViewById(R.id.tv_react_example).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyReactActivity.startActivity(MainActivity.this);
            }
        });
    }

    private void handleStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形View
            View statusView = createStatusView(this, getResources().getColor(R.color.bg_red_deep5));
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    private View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }
}
