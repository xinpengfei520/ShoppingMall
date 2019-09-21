package com.xpf.shoppingmall.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.base.BaseActivity;
import com.xpf.shoppingmall.base.BaseFragment;
import com.xpf.shoppingmall.fragment.CommunityFragment;
import com.xpf.shoppingmall.fragment.HomeFragment;
import com.xpf.shoppingmall.fragment.ShoppingCartFragment;
import com.xpf.shoppingmall.fragment.TypeFragment;
import com.xpf.shoppingmall.fragment.UserFragment;
import com.xsir.pgyerappupdate.library.PgyerApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int MESSAGE_BACK = 1;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_type)
    RadioButton rbType;
    @BindView(R.id.rb_community)
    RadioButton rbCommunity;
    @BindView(R.id.rb_cart)
    RadioButton rbCart;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private List<BaseFragment> fragments = new ArrayList<>();
    private int position = 0;
    private Fragment tempFragment;
    private TypeFragment typeFragment;

    private boolean isFlag = true;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_BACK) {
                isFlag = true; // 在2s时,恢复isFlag的变量值
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initListener();
        rgMain.check(R.id.rb_home);
        PgyerApi.checkUpdate(this);
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_home:
                    position = 0;
                    typeFragment.hideFragment();
                    break;
                case R.id.rb_type:
                    position = 1;
                    int currentTab = typeFragment.getCurrentTab();
                    if (currentTab == 0) {
                        if (typeFragment.typeListFragment != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .show(typeFragment.typeListFragment)
                                    .commit();
                        }
                    } else {
                        if (typeFragment.typeTagFragment != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .show(typeFragment.typeTagFragment)
                                    .commit();
                        }
                    }
                    break;
                case R.id.rb_community:
                    position = 2;
                    typeFragment.hideFragment();
                    break;
                case R.id.rb_cart:
                    position = 3;
                    fragments.remove(fragments.get(3));
                    ShoppingCartFragment cartFragment = new ShoppingCartFragment();
                    fragments.add(3, cartFragment);
                    typeFragment.hideFragment();
                    break;
                case R.id.rb_user:
                    position = 4;
                    typeFragment.hideFragment();
                    break;
                default:
                    position = 0;
                    break;
            }

            BaseFragment fragment = getFragment(position);
            switchFragment(tempFragment, fragment);
        });

    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position);
        }
        return null;
    }

    private void initFragment() {
        // 此处添加时有顺序，不然获取的时候按下标取会有问题!
        typeFragment = new TypeFragment();
        fragments.add(new HomeFragment());
        fragments.add(typeFragment);
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    // 隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    // 隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    public void toGoShopping() {
        rgMain.check(R.id.rb_home);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isFlag) {
            isFlag = false;
            Toast.makeText(MainActivity.this, "再点击一次返回键退出应用", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(MESSAGE_BACK, 2000);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 保证在activity退出前,移除所有未被执行的消息和回调方法,避免出现内存泄漏!
        handler.removeCallbacksAndMessages(null);
    }
}
