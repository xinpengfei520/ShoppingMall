package com.atguigu.shoppingmall.fragment;

import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.adapter.TagGridViewAdapter;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.domain.TagBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by xpf on 2016/11/24 :)
 * Wechat:18091383534
 * Function:分类标签页面的Fragment
 */

public class TypeTagFragment extends BaseFragment {

    @Bind(R.id.gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getDataFromNet(); //联网获取数据
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.TAG_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response); // 解析数据
                        adapter = new TagGridViewAdapter(mContext, result);
                        gvTag.setAdapter(adapter); // 设置适配器
                    }
                    break;

                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(String json) {
        result = new Gson().fromJson(json, TagBean.class).getResult();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
