package com.atguigu.shoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/11/23 :)
 * Wechat:18091383534
 * Function:搜索历史记录的适配器
 */

public class HistoryRecordAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> datas;

    public HistoryRecordAdapter(Context mContext, List<String> datas) {
        this.mContext = mContext;
        this.datas = datas;
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
            convertView = View.inflate(mContext, R.layout.item_history, null);
            ButterKnife.bind(convertView);
            holder = new ViewHolder(convertView);
            holder.tvHistory = (TextView) convertView.findViewById(R.id.tv_history);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String str = datas.get(position);
        holder.tvHistory.setText(str);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_history)
        TextView tvHistory;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
