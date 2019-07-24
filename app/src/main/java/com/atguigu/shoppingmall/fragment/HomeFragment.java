package com.atguigu.shoppingmall.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.activity.ScanResultActivity;
import com.atguigu.shoppingmall.activity.SearchActivity;
import com.atguigu.shoppingmall.adapter.HomeFragmentAdapter;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.domain.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.atguigu.shoppingmall.R.id.ib_top;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:主页面的fragment
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;
    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.ib_top)
    ImageButton ibTop;
    private ResultBeanData.ResultBean resultBean;
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        Log.e("TAG", "主页面的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "主页面的数据被初始化了");
        getDataFromNet();
    }

    // 连网请求数据
    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    // 当联网失败的时候的回调
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "连网请求失败===" + e.toString());
                    }

                    // 当连网成功的时候的回调
                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("TAG", "连网请求成功===" + response);
                        processData(response);
                    }

                });
    }

    private void processData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        Log.e("TAG", "解析成功了===" + resultBean.getHot_info().get(0).getName());

        if (resultBean != null) { // 有数据
            adapter = new HomeFragmentAdapter(mContext, resultBean);
            rvHome.setAdapter(adapter);

            // 设置布局管理者
            GridLayoutManager manager = new GridLayoutManager(mContext, 1);
            // 设置跨度大小监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) { // 隐藏
                        ibTop.setVisibility(View.GONE);
                    } else { // 显示
                        ibTop.setVisibility(View.VISIBLE);
                    }
                    return 1; // 只能返回1
                }
            });
            rvHome.setLayoutManager(manager); // 设置布局管理者

        } else { // 没有数据

        }
    }


    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.rv_home, ib_top, R.id.tv_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                mContext.startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.tv_message_home:
                break;
            case R.id.rv_home:
                break;
            case ib_top:
                rvHome.scrollToPosition(0); // 回到顶部
                break;
            case R.id.tv_scan:
                startActivity(new Intent(mContext, ScanResultActivity.class));
                break;
        }
    }

}
