package com.example.shang.recycleview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Shang on 2017/6/2.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyRecycleViewHolder>
        implements View.OnClickListener, ItemTouchHelperAdapter{
    private boolean flag;
    private Context context;
    private List<String> list;
    private RecyclerView recyclerView;// 与该adapter绑定的RecycleView

    private OnRecycleViewItemClickListener listener;

    /**
     * 拖动接口
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 删除接口
     * @param position
     */
    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    // 回调接口
    public interface OnRecycleViewItemClickListener{
        /**
         * @param view 点击的view
         * @param position view的位置
         */
        void onItemClick(View view , int position, String data);
    }

    public void setRecycleItemClickListener(OnRecycleViewItemClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 与recycleView绑定
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * 解绑
     * @param recyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }


    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item, null);
        // 为每个view添加点击事件
        view.setOnClickListener(this);
        return new MyRecycleViewHolder(view);
    }

    /**
     * 绑定数据
     * @param myRecycleViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(MyRecycleViewHolder myRecycleViewHolder, int i) {
        myRecycleViewHolder.tv_content.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyRecycleViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_content;

        public MyRecycleViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            int position = recyclerView.getChildAdapterPosition(v);
            String data = list.get(position);
            listener.onItemClick(v, (Integer) position, data);
        }
    }
}
