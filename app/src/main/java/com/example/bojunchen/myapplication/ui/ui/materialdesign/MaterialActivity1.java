package com.example.bojunchen.myapplication.ui.ui.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.ui.common.BaseActivity;

/**
 * 测试自定义Behavior：ScrollToTopBehavior
 *
 * 测试FloatingActionButton
 *
 * 测试Snackbar
 */
public class MaterialActivity1 extends BaseActivity {

    FloatingActionButton floatingActionButton;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MaterialActivity1.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material1);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        init();
    }

    protected void init() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(floatingActionButton, "Show The Snackbar", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
