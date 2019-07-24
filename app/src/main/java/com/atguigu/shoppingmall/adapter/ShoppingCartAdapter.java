package com.atguigu.shoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.domain.GoodsBean;
import com.atguigu.shoppingmall.utils.CartStorage;
import com.atguigu.shoppingmall.utils.Constants;
import com.atguigu.shoppingmall.view.AddSubView;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/11/22 :)
 * Wechat:18091383534
 * Function:购物车页面的适配器
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private Context mContext;
    private Button btnDelete;
    private Button btnCheckOut;
    private CheckBox cbAll;  //完成状态下的删除
    private TextView tvShopcartTotal; // 商品总价格
    private CheckBox checkboxAll;     // 是否全选
    private List<GoodsBean> datas;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> allGoods, TextView tvShopcartTotal,
                               CheckBox checkboxAll, CheckBox cbAll, Button btnCheckOut, Button btnDelete) {
        this.mContext = mContext;
        this.datas = allGoods;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        this.btnCheckOut = btnCheckOut;
        this.btnDelete = btnDelete;

        showTotalPrice();
        setListener();
        isCheckAll(); // 校验是否全选

        // 显示总数量
        showTotalCount();
        // 显示删除商品总数量
        showDeleteTotalCount();
    }

    // 设置点击某条的时候的监听(实现页面数据的变化)
    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // 1.根据位置找到bean对象
                GoodsBean goodsBean = datas.get(position);
                // 2.设置取反
                goodsBean.setSelected(!goodsBean.isSelected());
                // 3.刷新适配器
                notifyItemChanged(position);
                // 4.校验是否全选
                isCheckAll();
                // 5.重新计算总价格
                showTotalPrice();
            }
        });

        // 全选按钮的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.得到当前的全选按钮的状态
                boolean isCheckAll = checkboxAll.isChecked();
                // 2.根据状态设置全选和非全选
                checkAll_none(isCheckAll);
                // 3.计算总价格
                showTotalPrice();
                showTotalCount();
                showDeleteTotalCount();
            }
        });

        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.得到当前的全选按钮的状态
                boolean isCheckAll = cbAll.isChecked();
                // 2.根据状态设置全选和非全选
                checkAll_none(isCheckAll);
                showTotalCount();
                showDeleteTotalCount();
            }
        });
    }

    // 根据isCheck的值设置全选和非全选(遍历所有商品)
    public void checkAll_none(boolean isCheck) {

        if (datas != null && datas.size() > 0) {

            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    public void isCheckAll() {

        if (datas != null && datas.size() > 0) {

            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isSelected()) {
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    number++;
                }
            }
            if (number == datas.size()) {
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        } else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    // 显示商品的总价格
    public void showTotalPrice() {
        tvShopcartTotal.setText("￥" + getTotalPrice());
    }

    // 显示商品总数
    public void showTotalCount() {
        btnCheckOut.setText("去结算(" + getTotalCount() + ")");
    }

    // 显示删除商品总数
    public void showDeleteTotalCount() {
        btnDelete.setText("删除(" + getTotalCount() + ")");
    }

    // 计算商品的总价格
    public double getTotalPrice() {

        double totalPrice = 0.0;
        for (int i = 0; i < datas.size(); i++) {
            GoodsBean goodsBean = datas.get(i);
            if (goodsBean.isSelected()) {
                totalPrice += Double.valueOf(goodsBean.getCover_price()) * goodsBean.getNumber();
            }
        }
        return totalPrice;
    }

    /**
     * 得到选中商品的总数量
     */
    private int getTotalCount() {

        int count = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);//购物车类：是否被选中和多少个
                // 是否被勾选
                if (goodsBean.isSelected()) {
                    //得到数量，并且和之前的相加
                    count += goodsBean.getNumber();
                }
            }
        }
        return count;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        ButterKnife.bind(itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 1.根据位置得到对应的Bean对象
        final GoodsBean goodsBean = datas.get(position);
        // 2.设置数据
        holder.cbGov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());
        holder.ddSubView.setCurrentValue(goodsBean.getNumber());
        holder.ddSubView.setMinValue(1);
        holder.ddSubView.setMaxValue(15);

        holder.ddSubView.setOnAddSubClickListener(new AddSubView.OnAddSubClickListener() {
            @Override
            public void onNumberChange(int value) {
                // 1.内存中
                goodsBean.setNumber(value);
                // 2.本地
                CartStorage.getInstance().updateData(goodsBean);
                // 3.刷新适配器
                notifyItemChanged(position);
                // 4.再次计算总价格
                showTotalPrice();
                showTotalCount();
                showDeleteTotalCount();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()) {
                    // 从内存中移除
                    datas.remove(goodsBean);
                    // 从本地种移除
                    CartStorage.getInstance().deleteData(goodsBean);
                    // 刷新适配器
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_gov)
        CheckBox cbGov;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @BindView(R.id.ddSubView)
        AddSubView ddSubView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(getLayoutPosition());
                        showTotalCount();
                        showDeleteTotalCount();
                    }
                }
            });
        }

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // 当点击Item时的监听器
    public interface OnItemClickListener {
        // 当点击Item的时候回调
        void OnItemClick(int position);
    }

}
