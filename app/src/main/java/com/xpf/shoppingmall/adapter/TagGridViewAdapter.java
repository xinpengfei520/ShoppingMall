package com.xpf.shoppingmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.domain.TagBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/11/24 :)
 * Wechat:18091383534
 * Function:分类页面标签的网格布局的适配器
 */

public class TagGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<TagBean.ResultBean> result; // 接收数据
    private int[] colors = {Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, List<TagBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tab_gridview, null);
            ButterKnife.bind(convertView);
            holder = new ViewHolder(convertView);
            holder.tvTag = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取对应位置的数据
        TagBean.ResultBean resultBean = result.get(position);
        holder.tvTag.setText(resultBean.getName());
        holder.tvTag.setTextColor(colors[position % colors.length]); // 设置文本颜色

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
