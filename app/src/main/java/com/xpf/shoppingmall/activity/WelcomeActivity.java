package com.xpf.shoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.base.BaseActivity;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
