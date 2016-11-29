package com.atguigu.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScanResultActivity extends Activity {

    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.imageView)
    ImageView imageView;
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        ButterKnife.bind(this);

        startActivityForResult(new Intent(ScanResultActivity.this, CaptureActivity.class), requestCode);
    }

    /**
     * 处理返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            if (!TextUtils.isEmpty(scanResult)) {
                tvContent.setText(scanResult);
            } else {
                Toast.makeText(ScanResultActivity.this, "没有扫描到内容", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(ScanResultActivity.this, "取消扫描", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
