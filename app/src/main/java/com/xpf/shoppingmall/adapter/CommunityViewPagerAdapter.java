package com.xpf.shoppingmall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xpf.shoppingmall.fragment.HotPostFragment;
import com.xpf.shoppingmall.fragment.NewPostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xpf on 2016/11/24 :)
 * Wechat:18091383534
 * Function:发现页面的ViewPager的适配器
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"新帖", "热帖"};

    public CommunityViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        NewPostFragment newPostFragment = new NewPostFragment();
        HotPostFragment hotPostFragment = new HotPostFragment();
        fragments.add(newPostFragment);
        fragments.add(hotPostFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
