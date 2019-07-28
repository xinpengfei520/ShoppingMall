package com.xpf.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.domain.RegisterBean;
import com.xpf.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

// 注册账号页面
public class RegisterActivity extends Activity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.send_code)
    TextView sendCode;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.send_code, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.send_code:
                break;
            case R.id.btn_register:
                toRegister();
                break;
            default:
                break;
        }
    }

    // 去联网注册
    private void toRegister() {
        String account = etRegisterPhone.getText().toString().trim();
        String password = etRegisterPwd.getText().toString().trim();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {

            String registerUrl = Constants.REGISTER_BASE + account + Constants.REGISTER_PASSWORD + password;

            OkHttpUtils.get()
                    .url(registerUrl)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("TAG", "连网请求失败===" + e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("TAG", "RegisterActivity onResponse()==" + response);
                            parseData(response);
                        }
                    });

        } else {
            Toast.makeText(RegisterActivity.this, "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseData(String json) {
        if (!TextUtils.isEmpty(json)) {
            RegisterBean registerBean = JSON.parseObject(json, RegisterBean.class);
            int status = registerBean.getStatus();
            if (status == 200) { // 200只是一个随机数，实际开发中替换为注册成功的返回码
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                // 跳转到登录页面

                // 结束当前页面
            }
        }
    }
}
