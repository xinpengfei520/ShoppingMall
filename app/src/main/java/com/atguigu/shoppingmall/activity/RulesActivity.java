package com.atguigu.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RulesActivity extends Activity {

    @Bind(R.id.tv_agreed)
    TextView tvAgreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_agreed)
    public void onClick() {
        finish();
    }
}
