package com.xpf.shoppingmall.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.adapter.HotPostListViewAdapter;
import com.xpf.shoppingmall.base.BaseFragment;
import com.xpf.shoppingmall.domain.HotPostBean;
import com.xpf.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by xpf on 2016/11/24 :)
 * Wechat:18091383534
 * Function:热帖页面的Fragment
 */

public class HotPostFragment extends BaseFragment {

    @BindView(R.id.lv_hot_post)
    ListView lvHotPost;
    private List<HotPostBean.ResultBean> result;
    private HotPostListViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOT_POST_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        adapter = new HotPostListViewAdapter(mContext, result);
                        lvHotPost.setAdapter(adapter);
                    }
                    break;

                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(String json) {
        result = new Gson().fromJson(json, HotPostBean.class).getResult();
    }
}
