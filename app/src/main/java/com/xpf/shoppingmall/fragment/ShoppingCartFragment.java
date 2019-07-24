package com.xpf.shoppingmall.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.activity.MainActivity;
import com.xpf.shoppingmall.adapter.ShoppingCartAdapter;
import com.xpf.shoppingmall.base.BaseFragment;
import com.xpf.shoppingmall.domain.GoodsBean;
import com.xpf.shoppingmall.pay.PayResult;
import com.xpf.shoppingmall.pay.SignUtils;
import com.xpf.shoppingmall.utils.CartStorage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:购物页面的fragment
 */

public class ShoppingCartFragment extends BaseFragment {

    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.btn_tobuy)
    Button btnTobuy;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private ShoppingCartAdapter adapter;

    public static final int ACTION_EDIT = 1;    // 编辑状态
    public static final int ACTION_COMPLETE = 2;// 完成状态

    @Override
    public View initView() {

        Log.e("TAG", "购物面的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(this, view);

        initListener();

        return view;
    }

    private void initListener() {
        // 1.设置默认的编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) v.getTag();
                if (action == ACTION_EDIT) {
                    // 如果是编辑状态就切换为完成状态
                    showDelete();
                } else {
                    // 如果是完成状态就切换为编辑状态(默认显示"编辑")
                    hideDelete();
                }
            }
        });
    }

    private void hideDelete() {
        // 1.设置状态和文本
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);
        // 2.设置全选按钮和全部商品为非勾选
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.isCheckAll();
            adapter.showTotalPrice();
            adapter.showTotalCount();
        }
        // 3.显示结算视图
        llCheckAll.setVisibility(View.VISIBLE);
        // 4.隐藏删除视图
        llDelete.setVisibility(View.GONE);
    }

    private void showDelete() {
        // 1.设置状态和文本
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        // 2.设置全选按钮和全部商品为非勾选
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.isCheckAll();
            adapter.showTotalPrice();
            adapter.showDeleteTotalCount();
        }
        // 3.隐藏结算视图
        llCheckAll.setVisibility(View.GONE);
        // 4.显示删除视图
        llDelete.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "购物面的数据被初始化了");

        showData();
    }

    /**
     * 显示购物车页面的数据
     */
    private void showData() {
        List<GoodsBean> allGoods = CartStorage.getInstance().getAllGoods();
        if (allGoods != null && allGoods.size() > 0) {
            // 1.隐藏购物车为空页面
            rlNodata.setVisibility(View.GONE);
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
            // 2.设置适配器
            adapter = new ShoppingCartAdapter(mContext, allGoods, tvShopcartTotal, checkboxAll, cbAll, btnCheckOut, btnDelete);
            recyclerview.setAdapter(adapter);
            adapter.showTotalCount();

            // 设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        } else {
            showShoppingCartIsEmpty();
            // 2.设置适配器
        }
    }

    private void showShoppingCartIsEmpty() {
        // 1.显示购物车为空页面
        rlNodata.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.tv_shopcart_total,
            R.id.btn_check_out, R.id.ll_check_all, R.id.cb_all, R.id.btn_delete, R.id.btn_collection,
            R.id.ll_delete, R.id.btn_tobuy, R.id.rl_nodata})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                break;
            case R.id.checkbox_all:
                break;
            case R.id.tv_shopcart_total:
                break;
            case R.id.btn_check_out:
                pay(view); // 支付
                break;
            case R.id.ll_check_all:
                break;
            case R.id.cb_all:
                break;
            case R.id.btn_delete:
                // 删除选中的
                adapter.deleteData();
                // 校验状态
                adapter.isCheckAll();
                adapter.showTotalCount();
                if (adapter.getItemCount() == 0) {
                    showShoppingCartIsEmpty();
                }
                break;
            case R.id.btn_collection:
                break;
            case R.id.ll_delete:
                break;
            case R.id.btn_tobuy:
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.toGoShopping();
                break;
            case R.id.rl_nodata:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showData(); // 当加入购物车后再次进入到购物车页面时保证会显示加入的数据
    }

    ////////支付宝集成------ START ------/////////
    // 商户PID
    public static final String PARTNER = "2088911876712776";
    // 商户收款账号
    public static final String SELLER = "chenlei@atguigu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMRi0JtH+m1adnM3\n" +
            "tv2Z/FZQ3pbp+MVitQPNRfGdEbj9bjTfU1Stg3H/avoRmGl5lIMvpTwIhoaSNjSG\n" +
            "F9byNPjg6E82dmqsOvZ7UukZKoOSnGPBjICs0YoHIgBcWobbEZECFbr/F4SZfMDZ\n" +
            "f9AggxRFI7WwNCD3H2XRvRFXPsHzAgMBAAECgYBH81gwfBeRNwKMxg3iLieYTDqx\n" +
            "GfnN/5MIWI3WrtBOcXOOJYA3VvcAri64fffUNPonci5cp1b6ONlpNvPHCahEHmUn\n" +
            "n9cwpNsHSnaKQ5DvHO6rMTwznE5XuheMSdh+NLaFfzkKQ6150zGoXg5kP0A0NH/l\n" +
            "Vs9Lle+z90I2s0EQSQJBAOgQKRXk6/Op7KBcX2tFc/Vs7YIqzqferSUF3iDCBAn5\n" +
            "n7w9JTeepMfrDKJ4FQG9hgDbWlOcye4jAdKD1Kt8WcUCQQDYpJEpL16V+7VCb/p4\n" +
            "F+OdQG8jH6srRz1jmCMHOje5QbaM7CIypatzlJZat743R0BdQF94OEEG1v15RF5L\n" +
            "D0BXAkAaPCF48S1fmZk/s9GxveNTHqJnMdG1Fq56XgRzrZtpSUgurrbzZ8L3OvJt\n" +
            "t6egT73DgQjgJPyLKHf/RZoYXPGFAkBLsWQOkcBoguOX7OwEXcRnQwYRrZTusBtg\n" +
            "2t0SP2MM1Urk/fQM4hl/bqEB3UUWp0xzyHQS2wTNUPyLDDot24xNAkBGt3jXdiJM\n" +
            "7ItrTocnlIYgmodp7kCji7GH6QBE8BpFGzABnQFpD75rweRckUdqsZeoxZfy+een\n" +
            "yEVK/Xl84jG5";
    // 支付宝公钥 --公钥还要保持到服务器上
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEYtCbR/ptWnZzN7b9mfxWUN6W6fjFYrUDzUXxnRG4/W4031NUrYNx/2r6EZhpeZSDL6U8CIaGkjY0hhfW8jT44OhPNnZqrDr2e1LpGSqDkpxjwYyArNGKByIAXFqG2xGRAhW6/xeEmXzA2X/QIIMURSO1sDQg9x9l0b0RVz7B8wIDAQAB";

    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo("尚硅谷商城商品", "尚硅谷商城商品的详细描述", adapter.getTotalPrice() + "");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    ////////////支付宝集成------ END------////////////////
}
