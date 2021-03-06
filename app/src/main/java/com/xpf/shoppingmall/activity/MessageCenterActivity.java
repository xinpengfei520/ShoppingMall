package com.xpf.shoppingmall.activity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 消息中心页面
public class MessageCenterActivity extends BaseActivity {

    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ib_login_back)
    public void onClick() {
        finish();
    }
}
