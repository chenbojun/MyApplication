package com.example.bojunchen.myapplication.ui.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * MeasureUtil
 * Created by hzfengyuexin on 14-10-31.
 */
public class MeasureUtil {

	/**
	 * dp转pixel
	 */
	public static int dip2px(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5);
	}

	/**
	 * pixel转dp
	 */
	public static int px2dp(Context context, double px) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (px / density + 0.5);
	}

	public static Point getWindowPoint(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static int getWindowHeight(Context context) {
		return getMetrics(context).heightPixels;
	}

	public static int getWindowWidth(Context context) {
		return getMetrics(context).widthPixels;
	}

	public static float getDensity(Context context) {
		return getMetrics(context).density;
	}

	public static DisplayMetrics getMetrics(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric;
	}

	/**
	 * http://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
	 * Method for Setting the Height of the ListView dynamically.
	 * Hack to fix the issue of not showing all the items of the ListView
	 * when placed inside a ScrollView
	 *
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) { return; }

		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0) { view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, FrameLayout.LayoutParams.WRAP_CONTENT)); }

			view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
