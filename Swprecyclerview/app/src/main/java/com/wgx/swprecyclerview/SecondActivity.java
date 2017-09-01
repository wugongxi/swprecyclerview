package com.wgx.swprecyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import top.wgx.swprecyclerview.adapter.BRExpandAdapter;
import top.wgx.swprecyclerview.adapter.a.BaseRecyclerHolder;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class SecondActivity extends Activity{

    private RecyclerView rv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        rv_view = (RecyclerView) findViewById(R.id.rv_view);
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ArrayList<String> item1 = new ArrayList<>();
            for (int i1 = 0; i1 < 1 + new Random().nextInt(10); i1++) {
                item1.add("     group " + i + "item - " + i1);
                Log.e("0--item","group - "+i+" item-- i1");
            }
            data.add(item1);
        }
        rv_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MyAdaper adapter = new MyAdaper(R.layout.item_group, R.layout.item_child);
        rv_view.setAdapter(adapter);
        adapter.setList(data);



    }
    class MyAdaper extends BRExpandAdapter<ArrayList<String>> {

        public MyAdaper(int item_group, int item_child) {
            super(item_group, item_child);
        }

        @Override
        protected int getChildrenCount(ArrayList<String> strings, int group) {
            return strings.size();
        }

        @Override
        protected void bindGroup(Context context, BaseRecyclerHolder bHolder, ArrayList<String> position, int t) {
            bHolder.setText(R.id.tv_group,"  group--"+t);
        }

        @Override
        protected void bindChild(Context context, BaseRecyclerHolder bHolder, ArrayList<String> strings, int group, int child) {
            bHolder.setText(R.id.tv_child,strings.get(child));
        }

    }

}
