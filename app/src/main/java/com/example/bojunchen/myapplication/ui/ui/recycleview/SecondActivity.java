package com.example.bojunchen.myapplication.ui.ui.recycleview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.ui.common.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bojunchen on 2017/3/8.
 */

public class SecondActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        /**
         * recycleView的数据源和布局管理器设置
         */
        final MyAdapter myAdapter = new MyAdapter(this);
        final List<MyAdapter.ItemSource> datas = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            datas.add(new MyAdapter.ItemSource("学生" + i, "考试名次：" + i));
        }
        myAdapter.setData(datas);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         * Item移动结束后进行数据交换
         */
        ItemMoveInterface itemMoveInterface = new ItemMoveInterface() {
            @Override
            public void onItemMoved(int fromPosition, int toPosition) {
                if (fromPosition == 0 || toPosition == 0) {
                    return;
                } else {
                    Collections.swap(datas, fromPosition - 1, toPosition - 1);
                    myAdapter.notifyItemMoved(fromPosition, toPosition);
                }
            }

            @Override
            public void onDragStart() {
                Toast.makeText(SecondActivity.this, "开始移动", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragEnd() {
                Toast.makeText(SecondActivity.this, "结束移动", Toast.LENGTH_SHORT).show();
            }
        };

        /**
         * 设置ItemTouchHelper
         */
        ItemMoveCallBackImpl itemMoveCallBack = new ItemMoveCallBackImpl(itemMoveInterface);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemMoveCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        /**
         * 设置监听器，实现当用户点击Item左侧图标时允许拖动
         */
        myAdapter.setItemTouchListener(new MyAdapter.ItemTouchListener() {
            @Override
            public void onTouch(MyAdapter.ItemViewHolder itemViewHolder) {
                itemTouchHelper.startDrag(itemViewHolder);
            }
        });
    }

}
