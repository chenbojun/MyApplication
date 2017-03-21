package com.example.bojunchen.myapplication.ui.ui.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.utils.MeasureUtil;

import java.util.List;

/**
 * Created by bojunchen on 2017/3/9.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<ItemSource> mDatas;

    private ItemTouchListener itemTouchListener;

    private final int HEAD_TYPE = 0;
    private final int ITEM_TYPE = 1;

    public MyAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ItemSource> datas) {
        mDatas = datas;
    }

    public void setItemTouchListener(ItemTouchListener itemTouchListener) {
        this.itemTouchListener = itemTouchListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_TYPE;
        } else {
            return ITEM_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE) {
            TextView headView = new TextView(mContext);
            headView.setText("2017年高考学生成绩表：");
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = MeasureUtil.dip2px(mContext, 15);
            layoutParams.setMargins(margin, margin, margin, margin);
            headView.setLayoutParams(layoutParams);
            return new HeadViewHolder(headView);
        } else {
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_view, parent, false);
            return new ItemViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD_TYPE) {
            return;
        }
        if (viewType == ITEM_TYPE) {
            ((ItemViewHolder)holder).render(mDatas.get(position - 1));
            return;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 1 : mDatas.size() + 1;
    }

    public static class ItemSource {

        public String name;

        public String des;

        public ItemSource(String name, String des) {
            this.name = name;
            this.des = des;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView tvName;

        TextView tvDes;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDes = (TextView) itemView.findViewById(R.id.tv_des);
        }

        public void render(final ItemSource itemSoure) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int index = mDatas.indexOf(itemSoure);
                    MyAdapter.this.notifyItemRemoved(index + 1);
                    mDatas.remove(index);
                    return true;
                }
            });

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (itemTouchListener != null) {
                            itemTouchListener.onTouch(ItemViewHolder.this);
                        }
                    }
                    return true;
                }
            });

            if (!TextUtils.isEmpty(itemSoure.name)) {
                tvName.setText(itemSoure.name);
            } else {
                tvName.setText("");
            }

            if (!TextUtils.isEmpty(itemSoure.des)) {
                tvDes.setText(itemSoure.des);
            } else {
                tvDes.setText("");
            }
        }

    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ItemTouchListener {
        void onTouch(ItemViewHolder itemViewHolder);
    }
}
