package com.xpf.shoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.domain.ResultBeanData;
import com.xpf.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/11/22 :)
 * Wechat:18091383534
 * Function:推荐内容的适配器
 */

public class RecommendGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ResultBeanData.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = mContext;
        this.datas = recommend_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            holder = new ViewHolder(convertView);
            holder.ivRecommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据位置得到对应的数据
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = datas.get(position);

        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + recommendInfoBean.getFigure()).into(holder.ivRecommend);
        holder.tvName.setText(recommendInfoBean.getName());
        holder.tvPrice.setText("￥" + recommendInfoBean.getCover_price());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_recommend)
        ImageView ivRecommend;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
