package com.xpf.shoppingmall;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:
 */

public class MyApplication extends Application {

    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // 初始化讯飞语音,将“12345678”替换成您申请的APPID
        // 请勿在“=” 与 appid 之间添加任务空字符或者转义符
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=58397333");

        // 初始化okhttpClient
        initOkhttpClient();

        // 设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(true);
        // 初始化 JPush
        JPushInterface.init(this);

        // 初始化sharedSDK
        ShareSDK.initSDK(this);

        CrashReport.initCrashReport(getApplicationContext(), "a49bb24dcd", BuildConfig.DEBUG);
    }

    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build(); // 其他配置

        OkHttpUtils.initClient(okHttpClient);
    }
}
