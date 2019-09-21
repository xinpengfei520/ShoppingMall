package com.xpf.shoppingmall.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.adapter.MyOrderRecyclerAdapter;
import com.xpf.shoppingmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 订单页面
public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.activity_my_order)
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
