package com.example.bojunchen.myapplication.ui.ui.recycleview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bojunchen.myapplication.R;
import com.example.bojunchen.myapplication.ui.utils.MeasureUtil;

import java.util.Collections;
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

        TextView tvMoveToTop;

        TextView tvDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDes = (TextView) itemView.findViewById(R.id.tv_des);
            tvMoveToTop = (TextView) itemView.findViewById(R.id.tv_top);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
        }

        public void render(final ItemSource itemSoure) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final String[] btnTexts = {"置顶", "删除"};
                    final AlertDialog.Builder db =  new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
                    db.setCancelable(true)
                            .setItems(btnTexts, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0: {
                                            // 置顶
                                            int index = mDatas.indexOf(itemSoure);
                                            MyAdapter.this.notifyItemMoved(index + 1, 1);
                                            Collections.swap(mDatas, index, 0);
                                            break;
                                        }

                                        case 1: {
                                            // 删除
                                            int index = mDatas.indexOf(itemSoure);
                                            MyAdapter.this.notifyItemRemoved(index + 1);
                                            mDatas.remove(index);
                                            break;
                                        }
                                    }
                                }
                            });
                    db.create().show();
                    return true;
                }
            });

            // 查看详情页
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "进入学生成绩详情页面！", Toast.LENGTH_SHORT).show();
                }
            });

            // 置顶
            tvMoveToTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mDatas.indexOf(itemSoure);
                    MyAdapter.this.notifyItemMoved(index + 1, 1);
                    Collections.swap(mDatas, index, 0);
                }
            });

            // 删除
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mDatas.indexOf(itemSoure);
                    MyAdapter.this.notifyItemRemoved(index + 1);
                    mDatas.remove(index);
                }
            });

            // 设置点击左侧图片可移动卡片
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
