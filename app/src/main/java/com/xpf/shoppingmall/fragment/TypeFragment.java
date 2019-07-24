package com.xpf.shoppingmall.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.activity.SearchActivity;
import com.xpf.shoppingmall.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:分类页面的fragment
 */

public class TypeFragment extends BaseFragment {

    @BindView(R.id.segTabLayout)
    SegmentTabLayout segTabLayout;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @BindView(R.id.fl_type)
    FrameLayout flType;
    private List<BaseFragment> fragmentList;
    private Fragment fragment;
    public TypeListFragment typeListFragment; // 列表类型的Fragment
    public TypeTagFragment typeTagFragment;   // 标签页面的Fragment
    private int currentTab;

    @Override
    public View initView() {

        Log.e("TAG", "分类面的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "分类面的数据被初始化了");

        initFragment();

        String[] titles = {"分类", "标签"};
        segTabLayout.setTabData(titles);

        // 设置Tab切换时的监听
        segTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragment, fragmentList.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initFragment() {

        fragmentList = new ArrayList<>();
        typeListFragment = new TypeListFragment();
        typeTagFragment = new TypeTagFragment();
        fragmentList.add(typeListFragment);
        fragmentList.add(typeTagFragment);

        switchFragment(fragment, fragmentList.get(0));
    }

    // 切换到不同的Fragment
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (segTabLayout != null) {
            currentTab = segTabLayout.getCurrentTab();
        }
        if (fragment != nextFragment) {
            fragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                // 判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    // 隐藏当前的Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment, "tagFragment").commit();
                } else {
                    // 隐藏当前的Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    public void hideFragment() {
        if (typeListFragment != null && typeTagFragment != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.hide(typeListFragment).hide(typeTagFragment).commit();
        }
    }

    public int getCurrentTab() {
        return currentTab;
    }

    @OnClick({R.id.iv_type_search, R.id.fl_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_type_search:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.fl_type:
                break;
        }
    }

}
