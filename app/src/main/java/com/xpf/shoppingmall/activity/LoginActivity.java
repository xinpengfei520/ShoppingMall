package com.xpf.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xpf.shoppingmall.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;

import static android.R.attr.action;

// 登录页面
public class LoginActivity extends Activity {

    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.ib_weibo)
    ImageButton ibWeibo;
    @BindView(R.id.ib_qq)
    ImageButton ibQq;
    @BindView(R.id.ib_wechat)
    ImageButton ibWechat;
    @BindView(R.id.tv_rules)
    TextView tvRules;

    private String screen_name;
    private String profile_image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initListener();
    }


    private void initListener() {
        etLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(password) && password.length() >= 6) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.ib_login_back, R.id.btn_login, R.id.tv_rules, R.id.ib_weibo, R.id.ib_qq, R.id.ib_wechat, R.id.tv_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                finish();
                break;

            case R.id.btn_login:
                break;
            case R.id.tv_rules:
                startActivity(new Intent(LoginActivity.this, RulesActivity.class));
                break;
            case R.id.ib_weibo:
                // 新浪微博
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(sina);
                break;
            case R.id.ib_qq:
                // QQ空间
                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                authorize(qzone);
                break;
            case R.id.ib_wechat:
                Toast.makeText(LoginActivity.this, "微信登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    // 如果使用其他方式登录时返回的带结果的回调
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String loginUrl = Constants.LOGIN_BASE + "s" + Constants.LOGIN_PASSWORD + "34";
//        if (resultCode == RESULT_OK) {
//            OkHttpUtils.get().url(loginUrl)
//                    .build().execute(new Callback() {
//                @Override
//                public Object parseNetworkResponse(Response response, int id) throws Exception {
//                    return null;
//                }
//
//                @Override
//                public void onError(Call call, Exception e, int id) {
//
//                }
//
//                @Override
//                public void onResponse(Object response, int id) {
//                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
//        }
//    }

    ////////// 集成第三方登录 start /////////////////
    private static final int MSG_AUTH_CANCEL = 2;  // 取消授权
    private static final int MSG_AUTH_ERROR = 3;   // 错误授权
    private static final int MSG_AUTH_COMPLETE = 4;// 授权完成

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUTH_CANCEL: { // 取消授权
                }
                break;

                case MSG_AUTH_ERROR: { // 授权失败
                }
                break;

                case MSG_AUTH_COMPLETE: { // 授权成功
                    Object[] objs = (Object[]) msg.obj;
                    String platform = (String) objs[0];
                    HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
                }
                break;
            }
            return false;
        }
    });

    // 执行授权,获取用户信息
    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }
        plat.setPlatformActionListener(new MyPlatformActionListener());
        plat.SSOSetting(true);// 关闭SSO授权
        plat.showUser(null);
    }

    class MyPlatformActionListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            if (action == Platform.ACTION_USER_INFOR) {
                Message msg = new Message();
                msg.what = MSG_AUTH_COMPLETE;
                msg.obj = new Object[]{platform.getName(), hashMap};
                handler.sendMessage(msg);

                // 获取登录的名字
                screen_name = platform.getDb().getUserName();
                // 获取登录的图片
                profile_image_url = platform.getDb().getUserIcon();
                Intent intent = getIntent();
                intent.putExtra("screen_name", screen_name);
                intent.putExtra("profile_image_url", profile_image_url);
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            if (action == Platform.ACTION_USER_INFOR) {
                handler.sendEmptyMessage(MSG_AUTH_ERROR);
            }
        }

        @Override
        public void onCancel(Platform platform, int i) {
            if (action == Platform.ACTION_USER_INFOR) {
                handler.sendEmptyMessage(MSG_AUTH_CANCEL);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this); // 停止ShareSDK
    }
}
