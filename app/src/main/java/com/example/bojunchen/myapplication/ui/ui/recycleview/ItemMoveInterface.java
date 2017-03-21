package com.example.bojunchen.myapplication.ui.ui.recycleview;

/**
 * Created by bojunchen on 2017/3/9.
 */

public interface ItemMoveInterface {

    /**
     * Item 切换位置
     *
     * @param fromPosition 开始位置
     * @param toPosition   结束位置
     */
    void onItemMoved(int fromPosition, int toPosition);

    /**
     * 拖动开始
     */
    void onDragStart();

    /**
     * 拖动结束
     */
    void onDragEnd();
}
