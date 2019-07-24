package com.xpf.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.domain.ResultBeanData;
import com.xpf.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by xpf on 2016/11/22 :)
 * Wechat:18091383534
 * Function:秒杀内容的适配器
 */

public class SecKillRecyclerViewAdapter extends RecyclerView.Adapter<SecKillRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SecKillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_seckill, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // 1.根据位置得到对应的数据
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);

        // 2.绑定数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(holder.iv_figure);
        holder.tv_cover_price.setText(listBean.getCover_price());
        holder.tv_origin_price.setText(listBean.getOrigin_price());
    }

    // 返回秒杀条目的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, "秒杀===" + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    if(onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnItem的点击事件的监听器
     */
    public interface OnSeckillRecyclerView {
        void onItemClick(int layoutPosition);
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;

    /**
     * @param onSeckillRecyclerView 设置Item的点击事件
     */
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
