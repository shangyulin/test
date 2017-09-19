package com.example.shang.recycleview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SimpleItemTouchHelperCallback.OnRemoveConfirmListener {

    private RecyclerView recyclerView;
    private List<String> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        list = new LinkedList<>();
        for (int i = 1; i <= 100; i++){
            list.add("RecycleView原始数据" + i);
        }
        adapter = new MyAdapter(this, list);
        // 自定义布局格式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        callback.setOnRemoveConfirmListener(this);
        // 处理点击事件
        adapter.setRecycleItemClickListener(new MyAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String data) {
                Toast.makeText(MainActivity.this, "The position is "+position+", the data is "+data, Toast.LENGTH_LONG).show();
                adapter.remove(position);
            }
        });
    }

    @Override
    public void onremove(final int position) {
        final String content = list.get(position);
        list.remove(position);
        adapter.notifyItemRemoved(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("warn")
                .setMessage("确定删除")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.add(position, content);
                        adapter.notifyItemInserted(position);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
