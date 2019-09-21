package com.xpf.shoppingmall.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.adapter.HistoryRecordAdapter;
import com.xpf.shoppingmall.base.BaseActivity;
import com.xpf.shoppingmall.utils.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private EditText et_input;
    private ImageView iv_delete;
    private ImageView iv_voice;
    private ListView lv_history;
    private ImageView iv_deleteAllHistory;
    private HistoryRecordAdapter adapter;
    private List<String> datas = new ArrayList<>();
    private Context mContext;
    // 用HashMap存储讯飞语音听写的结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        et_input = (EditText) findViewById(R.id.et_input);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_voice = (ImageView) findViewById(R.id.iv_voice);
        lv_history = (ListView) findViewById(R.id.lv_history);
        iv_deleteAllHistory = (ImageView) findViewById(R.id.iv_deleteAllHistory);

        initHistoryList(); // 初始化历史搜索记录列表

        /**
         * 给sv_search设置一个文本改变的监听
         * 注 ： 参数：需要一个TextWatcher的对象，而TextWatcher是一个接口，所以需要一个接口的实现类
         */
        et_input.addTextChangedListener(new inputLetterChangerListener());

        iv_deleteAllHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.clear();
                adapter.notifyDataSetChanged(); // 刷新适配器
            }
        });

        iv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialogI();
            }
        });
    }

    /**
     * 语音去搜索的方法
     */
    private void showInputDialogI() {
        // 1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        // 2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");  // 中文
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin"); // 普通话
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        // 3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        // 4.显示dialog，接收语音输入
        mDialog.show();
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {

            if (i != ErrorCode.SUCCESS) {
                Toast.makeText(SearchActivity.this, "抱歉,初始化出错了~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * 返回结果
         */
        @Override
        public void onResult(RecognizerResult results, boolean b) {
            String result = results.getResultString();
            String text = JsonParser.parseIatResult(result);

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }

            String content = resultBuffer.toString();
            // 将语音识别后末尾的"。"号去掉
            content = content.replace("。", "");

            et_input.setText(content);
            et_input.setSelection(et_input.length());
            Log.e("TAG", result);
        }

        /**
         * 语音输入失败
         *
         * @param speechError:
         */
        @Override
        public void onError(SpeechError speechError) {

        }
    }

    private void initHistoryList() {

        // 从本地存储中获取保存的历史搜索数据
//        String history_list = CacheUtils.getString(mContext, "history_list");
//        char[] chars = history_list.toCharArray();
        datas.add("羽绒服");
        datas.add("围巾");
        datas.add("靴子");

        // 显示历史搜索记录
        if (datas != null && datas.size() > 0) {
            adapter = new HistoryRecordAdapter(mContext, datas);
            lv_history.setAdapter(adapter);
        } else { // 没有历史搜索记录

        }
    }

    public void delete(View v) {
        String text = et_input.getText().toString();

        if (!TextUtils.isEmpty(text)) {
            et_input.setText("");
            iv_delete.setVisibility(View.GONE);
        }
    }

    /**
     * 点击取消的回调
     *
     * @param v
     */
    public void cancel(View v) {
        finish();
    }

    private class inputLetterChangerListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(s.toString())) {
                iv_delete.setVisibility(View.VISIBLE);

            } else {
                iv_delete.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    public void search(View v) {
        // 当点击搜索按钮的时候将数据加入到集合中，并保存到本地存储中
    }
}
