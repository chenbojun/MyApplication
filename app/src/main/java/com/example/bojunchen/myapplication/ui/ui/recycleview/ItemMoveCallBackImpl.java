package com.example.bojunchen.myapplication.ui.ui.recycleview;

import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by bojunchen on 2017/3/9.
 */

public class ItemMoveCallBackImpl extends ItemTouchHelper.Callback{


    private ItemMoveInterface itemMoveInterface;

    private boolean isElevated = false;

    public ItemMoveCallBackImpl(ItemMoveInterface itemMoveInterface) {
        this.itemMoveInterface = itemMoveInterface;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (itemMoveInterface != null) {
            itemMoveInterface.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (itemMoveInterface != null) {
            if (viewHolder == null) {
                itemMoveInterface.onDragEnd();
            } else {
                itemMoveInterface.onDragStart();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        // 拖动时添加阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isCurrentlyActive && !isElevated) {
            final float newElevation = 10f + ViewCompat.getElevation(viewHolder.itemView);
            ViewCompat.setElevation(viewHolder.itemView, newElevation);
            isElevated = true;
        }

    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        isElevated = false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

//    private int mDragStartPosition; //能够拖拽的开始位置
//
//    /**
//     * 设置拖拽开始位置
//     *
//     * @param dragStartPosition 开始位置
//     */
//    public void setDragStartPosition(int dragStartPosition) {
//        mDragStartPosition = dragStartPosition;
//    }
//
//    private int mDragEndPosition; //能够拖拽的结束位置
//    private boolean mDragEndPositionFlag; //是否设置了拖拽结束位置
//
//    /**
//     * 设置拖拽结束的位置
//     *
//     * @param dragEndPosition 结束位置
//     */
//    public void setDragEndPosition(int dragEndPosition) {
//        mDragEndPositionFlag = true;
//        mDragEndPosition = dragEndPosition;
//    }
//
//    /**
//     * 根据方向和条件获取限制在RecyclerView内部的DY值
//     *
//     * @param recyclerView 列表
//     * @param viewHolder   drag的ViewHolder
//     * @param dY           限制前的DY值
//     * @return 限制后的DY值
//     */
//    private float getLimitedDy(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dY) {
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//        if (!(layoutManager instanceof LinearLayoutManager)
//                || ((LinearLayoutManager) layoutManager).getOrientation() != OrientationHelper.VERTICAL) {
//            return dY;
//        }
//        int position = viewHolder.getLayoutPosition();
//        mDragEndPosition = mDragEndPositionFlag ?
//                mDragStartPosition : recyclerView.getAdapter().getItemCount() - 1;
//        if (position == mDragStartPosition) {
//            return dY < 0 ? 0 : dY;
//        } else if (position == mDragEndPosition) {
//            return dY > 0 ? 0 : dY;
//        }
//        return dY;
//    }
}
