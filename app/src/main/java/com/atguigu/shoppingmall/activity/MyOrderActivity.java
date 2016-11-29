package com.atguigu.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.adapter.MyOrderRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 订单页面
public class MyOrderActivity extends Activity {

    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.iv_back)
    ImageView ivBack;
//    @Bind(R.id.indicator)
//    TabPageIndicator indicator;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.activity_my_order)
    LinearLayout activityMyOrder;
    private MyOrderRecyclerAdapter adapter;

    private String[] titles = {"全部", "待付款", "待发货", "待收货", "待评价"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        adapter = new MyOrderRecyclerAdapter(this);
        recyclerview.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 1);
//        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
    }

    @OnClick({R.id.iv_search, R.id.iv_more, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                Toast.makeText(MyOrderActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_more:
                Toast.makeText(MyOrderActivity.this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
