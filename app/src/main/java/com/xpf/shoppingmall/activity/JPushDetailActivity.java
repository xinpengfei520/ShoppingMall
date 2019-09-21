package com.xpf.shoppingmall.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.base.BaseActivity;

// 点击推送后进入的页面
public class JPushDetailActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_detail);

        webView = (WebView) findViewById(R.id.webView);

        String url = getIntent().getStringExtra("url");

        WebSettings webSettings = webView.getSettings();
        // 设置webView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        // 加载需要显示的网页
        webView.loadUrl(url);
        // 设置Web视图
        webView.setWebViewClient(new WebViewClient());
    }
}
