package com.xpf.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xpf.shoppingmall.R;

/**
 * Created by xpf on 2016/11/25 :)
 * Wechat:18091383534
 * Function:我的订单页面的适配器
 */

public class MyOrderRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    private static final int ORDER = 1;
    private final Context mContext;
    private LayoutInflater mLayoutInflater; // 初始化布局加载器

    public MyOrderRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == ORDER) {
            return new OrderViewHolder(mContext, mLayoutInflater.inflate(R.layout.order_item, null));
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == ORDER) {
//            OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
//            orderViewHolder.setData();
//        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return ORDER;
//    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;

        public OrderViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
        }

//        public void setData() {
//
//        }
    }
}
