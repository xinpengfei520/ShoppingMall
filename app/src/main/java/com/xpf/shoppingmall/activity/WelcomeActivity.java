package com.xpf.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.xpf.shoppingmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends Activity {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    //该程序模拟天成长度为100的数组
    private int[] data = new int[100];

    int hasData = 0;

    // 记录ProgressBar的完成进度
    int progressStatus = 0;

    // 创建一个复杂更新进度的Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x111) {
                progressbar.setProgress(progressStatus);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

//        startLoading();
        // 启动线程来执行任务
//        new Thread() {
//            public void run() {
//                while (progressStatus < 100) {
//                    // 获取耗时的完成百分比
//                    progressStatus = doWork();
//                    Message m = new Message();
//                    m.what = 0x111;
//                    // 发送消息到Handler
//                    handler.sendMessage(m);
//                }
//            }
//        }.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);

    }

    // 模拟一个耗时的操作
    private int doWork() {
        data[hasData++] = (int) (Math.random() * 100);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasData;
    }

//    private void startLoading() {
//
//        progressbar.setProgress(0);
//        progressbar.setMax(100);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 通过循环的方式显示进度
//                for (int i = 0; i < 100; i++) {
//                    progressbar.incrementProgressBy(1);
//                    SystemClock.sleep(100);
//                }
//            }
//        }).start();
//    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
