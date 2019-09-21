package com.xpf.shoppingmall.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.adapter.TypeLeftAdapter;
import com.xpf.shoppingmall.adapter.TypeRightAdapter;
import com.xpf.shoppingmall.base.BaseFragment;
import com.xpf.shoppingmall.domain.TypeBean;
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
 * Function:分类列表页面(左边为ListView,右边为RecyclerView)
 */

public class TypeListFragment extends BaseFragment {

    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;
    private FrameLayout fl_list_container;
    private List<TypeBean.ResultBean> result;
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private TypeLeftAdapter leftAdapter; // 左边ListView的适配器
    private boolean isFirst = true;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_typelist, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet(urls[0]);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils.get()
                .url(url)
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
                        processData(response);
                        if (isFirst) {
                            leftAdapter = new TypeLeftAdapter(mContext);
                            lvLeft.setAdapter(leftAdapter);
                        }
                        initListener(leftAdapter);

                        // 解析右边数据,设置RecyclerView的适配器
                        TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext, result);
                        rvRight.setAdapter(rightAdapter);

                        // 设置Grid网格布局的布局管理器,当是position是1时列跨度为3,即占3列
                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                if (position == 0) {
                                    return 3;
                                } else {
                                    return 1;
                                }
                            }
                        });
                        rvRight.setLayoutManager(manager);
                    }
                    break;

                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // 设置右边ListView的监听事件
    private void initListener(final TypeLeftAdapter adapter) {

        // 点击监听
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position); // 刷新
                if (position != 0) {
                    isFirst = false;
                }
                getDataFromNet(urls[position]);
                leftAdapter.notifyDataSetChanged();
            }
        });

        // 设置选中的监听
        lvLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 解析json数据
    private void processData(String json) {
        result = new Gson().fromJson(json, TypeBean.class).getResult();
    }

}
