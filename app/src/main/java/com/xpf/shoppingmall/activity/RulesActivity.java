package com.xpf.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xpf.shoppingmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RulesActivity extends Activity {

    @BindView(R.id.tv_agreed)
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