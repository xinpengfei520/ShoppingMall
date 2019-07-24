package com.xpf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xpf.shoppingmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/11/24 :)
 * Wechat:18091383534
 * Function:左边分类页面的数据的适配器
 */

public class TypeLeftAdapter extends BaseAdapter {

    private Context mContext;
    private int mSelect = 0; // 选中项
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件",
            "包包", "装扮", "居家宅品", "办公文具", "数码周边", "游戏专区"};

    public TypeLeftAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_type, null);
            ButterKnife.bind(convertView);
            holder = new ViewHolder(convertView);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(titles[position]);

        if (mSelect == position) {
            convertView.setBackgroundResource(R.drawable.type_item_background_selector);
            holder.tvTitle.setTextColor(Color.parseColor("#fd3f3f"));
        } else {
            convertView.setBackgroundResource(R.drawable.bg2);  // 其他项背景
            holder.tvTitle.setTextColor(Color.parseColor("#323437"));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // 如果position变了就刷新的方法
    public void changeSelected(int positon) {
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }
}
