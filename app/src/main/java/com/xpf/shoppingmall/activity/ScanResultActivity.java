package com.xpf.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xpf.shoppingmall.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanResultActivity extends Activity {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.activity_scan_result)
    LinearLayout activityScanResult;
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
