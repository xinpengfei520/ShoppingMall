package com.atguigu.shoppingmall.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.adapter.CommunityViewPagerAdapter;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:发现页面的fragment
 */

public class CommunityFragment extends BaseFragment {

    @BindView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @BindView(R.id.pager_indicator)
    TabPageIndicator pagerIndicator;
    @BindView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private CommunityViewPagerAdapter adapter;

    @Override
    public View initView() {
        Log.e("TAG", "发现面的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        ButterKnife.bind(this, view);
        adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        pagerIndicator.setVisibility(View.VISIBLE);
        pagerIndicator.setViewPager(viewPager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "发现面的数据被初始化了");
    }

    @OnClick({R.id.ib_community_icon, R.id.pager_indicator, R.id.ib_community_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_community_icon:
                break;
            case R.id.pager_indicator:
                break;
            case R.id.ib_community_message:
                break;
        }
    }

}
