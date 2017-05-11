package com.example.bojunchen.myapplication.ui.ui.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.ui.common.BaseActivity;

/**
 * 测试AppBarLayout
 *
 * Created by bojunchen on 2017/5/11.
 */

public class MaterialActivity2 extends BaseActivity {

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, MaterialActivity2.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material2);
		getWindow().addFlags(Window.FEATURE_NO_TITLE);
		init();
	}

	protected void init() {
	}
}
