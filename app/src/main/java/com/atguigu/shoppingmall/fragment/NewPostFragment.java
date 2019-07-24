package com.atguigu.shoppingmall.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.adapter.NewPostListViewAdapter;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.domain.NewPostBean;
import com.atguigu.shoppingmall.utils.Constants;
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
 * Function:新帖页面的Fragment
 */

public class NewPostFragment extends BaseFragment {

    @BindView(R.id.lv_new_post)
    ListView lvNewPost;
    private List<NewPostBean.ResultBean> result;
    private NewPostListViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_new_post, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.NEW_POST_URL)
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
                        adapter = new NewPostListViewAdapter(mContext, result);
                        lvNewPost.setAdapter(adapter); // 设置适配器
                    }
                    break;

                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(String json) {
        result = new Gson().fromJson(json, NewPostBean.class).getResult();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
