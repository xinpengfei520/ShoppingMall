package com.atguigu.shoppingmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.activity.GoodsInfoActivity;
import com.atguigu.shoppingmall.domain.GoodsBean;
import com.atguigu.shoppingmall.domain.TypeBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.atguigu.shoppingmall.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by xpf on 2016/11/24 :)
 * Wechat:18091383534
 * Function:右边数据类型的适配器
 */

public class TypeRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    public static final int HOT = 0;
    public static final int ORDINARY = 1;

    private LayoutInflater mLayoutInflater;
    private List<TypeBean.ResultBean.ChildBean> child; // 头布局的数据
    private List<TypeBean.ResultBean.HotProductListBean> hot_product_list; // 热卖商品数据
    public int currentPostion;

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);

        if (result != null && result.size() > 0) {
            child = result.get(0).getChild();
            hot_product_list = result.get(0).getHot_product_list();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.item_hot_right, null), mContext);
        } else {
            return new OrdinaryViewHolder(mLayoutInflater.inflate(R.layout.item_ordinary_right, null), mContext);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(hot_product_list);
        } else {
            OrdinaryViewHolder ordinaryViewHolder = (OrdinaryViewHolder) holder;
            ordinaryViewHolder.setData(child.get(position - 1), position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return child.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentPostion = HOT;
        } else {
            currentPostion = ORDINARY;
        }
        return currentPostion;
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private GridView gv_ordinary;
        private ImageView iv_ordinary_right;
        private TextView tv_ordinary_right;
        private LinearLayout ll_root;

        public OrdinaryViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            iv_ordinary_right = (ImageView) itemView.findViewById(R.id.iv_ordinary_right);
            tv_ordinary_right = (TextView) itemView.findViewById(R.id.tv_ordinary_right);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }

        public void setData(TypeBean.ResultBean.ChildBean childBean, final int position) {
            Glide.with(mContext)
                    .load(Constants.BASE_URL_IMAGE + childBean.getPic())
                    .into(iv_ordinary_right);
            tv_ordinary_right.setText(childBean.getName());
            ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "position===" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linear;
        private Context mContext;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            linear = (LinearLayout) itemView.findViewById(R.id.linear);
        }

        public void setData(final List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {

            for (int i = 0; i < hot_product_list.size(); i++) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                final LinearLayout myLinear = new LinearLayout(mContext);
                params.setMargins(DensityUtil.dip2px(mContext, 5), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));
                myLinear.setOrientation(LinearLayout.VERTICAL);

                myLinear.setTag(i);

                linear.addView(myLinear, params);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                ImageView imageView = new ImageView(mContext);

                Glide.with(mContext)
                        .load(Constants.BASE_URL_IMAGE + hot_product_list.get(i).getFigure())
                        .into(imageView);
                lp.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));
                myLinear.addView(imageView, lp);

                LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(mContext);
                textView.setText("￥" + hot_product_list.get(i).getCover_price());
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                myLinear.addView(textView, textViewLp);

                // 设置点击事件
                myLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) myLinear.getTag();

                        String cover_price = hot_product_list.get(i).getCover_price();
                        String name = hot_product_list.get(i).getName();
                        String figure = hot_product_list.get(i).getFigure();
                        String product_id = hot_product_list.get(i).getProduct_id();
                        GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra("goods_bean", goodsBean);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }
}
