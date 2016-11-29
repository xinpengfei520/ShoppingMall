package com.atguigu.shoppingmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.fragment.CommunityFragment;
import com.atguigu.shoppingmall.fragment.HomeFragment;
import com.atguigu.shoppingmall.fragment.ShoppingCartFragment;
import com.atguigu.shoppingmall.fragment.TypeFragment;
import com.atguigu.shoppingmall.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_type)
    RadioButton rbType;
    @Bind(R.id.rb_community)
    RadioButton rbCommunity;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    private static final int MESSAGE_BACK = 1;
    private List<BaseFragment> fragments = new ArrayList<>();
    private int position = 0;
    private Fragment tempFragemnt;
    private TypeFragment typeFragment;

    private boolean isFlag = true;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_BACK:
                    isFlag = true; // 在2s时,恢复isFlag的变量值
                    break;
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
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
                BaseFragment frament = getFrament(position);
                switchFragment(tempFragemnt, frament);
            }
        });

    }

    private BaseFragment getFrament(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
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
        if (tempFragemnt != nextFragment) {
            tempFragemnt = nextFragment;
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
        rgMain.check(R.id.home);
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
