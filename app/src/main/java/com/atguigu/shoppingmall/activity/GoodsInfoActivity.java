package com.atguigu.shoppingmall.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.domain.GoodsBean;
import com.atguigu.shoppingmall.utils.CartStorage;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

// 商品详情页面
public class GoodsInfoActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @BindView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @BindView(R.id.tv_more_home)
    TextView tvMoreHome;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.activity_goods_info)
    LinearLayout activityGoodsInfo;

    private GoodsBean goodsBean;

    private PopupWindow popupWindow;      // 声明PopupWindow
    private View popupView;               // 声明PopupWindow对应的视图
    private ScaleAnimation scaleAnimation;// 声明缩放动画

    private PopupWindow pwStyle;          // 声明PopupWindow
    private View pwStyleView;             // 声明PopupWindow对应的视图
    private TranslateAnimation translateAnimation;// 声明缩放动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);

        // 接收数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goods_bean");
        if (goodsBean != null) {
            setGoodsDetailsView(goodsBean);
        }
    }

    private void setGoodsDetailsView(GoodsBean goodsBean) {

        // 设置商品的图片
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(ivGoodInfoImage);

        // 设置商品的价格
        tvGoodInfoPrice.setText("￥" + goodsBean.getCover_price());
        // 设置商品的文字
        tvGoodInfoName.setText(goodsBean.getName());

        setWebView(goodsBean.getProduct_id());
    }

    private void setWebView(String product_id) {

        if (product_id != null) {
            wbGoodInfoMore.loadUrl("http://m.taobao.com");
            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true); // 设置使用javaScript
            settings.setUseWideViewPort(true);   // 设置支持双击页面变大
            // 设置优先使用网络缓存
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true; // 返回值为true的时候表示使用webview打开，为false表示使用系统的浏览器或者使用第三方的浏览器打开
                }
            });
        }
    }

    @Override
    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.iv_good_info_image,
            R.id.tv_good_info_style, R.id.wb_good_info_more, R.id.tv_good_info_callcenter,
            R.id.tv_good_info_collection, R.id.tv_good_info_cart,
            R.id.btn_good_info_addcart, R.id.ll_goods_root, R.id.tv_more_share,
            R.id.tv_more_search, R.id.tv_more_home, R.id.btn_more, R.id.ll_root,
            R.id.activity_goods_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;

            case R.id.ib_good_info_more:
                showPopupWindow(view); // 右上角更多按钮
                break;

            case R.id.iv_good_info_image:
                // 点击商品图片在photoView中显示(预留)
                break;

            case R.id.tv_good_info_style:
                selectStyle(); // 选择款式按钮
                break;

            case R.id.wb_good_info_more:
                break;

            case R.id.tv_good_info_callcenter:
                Toast.makeText(this, "客户中心", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                // 此处的收藏可将收藏的按钮变为实心,表示收藏,将收藏信息保存到本地和服务器上
                break;

            case R.id.tv_good_info_cart:
                Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
                toShoppingCartPager();
                break;

            case R.id.btn_good_info_addcart:
                CartStorage.getInstance().addData(goodsBean);
                Toast.makeText(this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_goods_root:
                break;

            case R.id.tv_more_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_more_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_more_home:
                Toast.makeText(this, "主页面", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_more:
                break;
            case R.id.ll_root:
                break;
            case R.id.activity_goods_info:
                break;
        }
    }

    // 选择款式的的popupWindow
    private void selectStyle() {

        if (pwStyle == null) {
            // 设置pwStyleView
            pwStyleView = LayoutInflater.from(GoodsInfoActivity.this).inflate(R.layout.item_pwstyleviw, null);
            pwStyle = new PopupWindow(pwStyleView,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            pwStyle.setContentView(pwStyleView);

            // 设置背景图片， 必须设置，不然动画没作用
            pwStyle.setBackgroundDrawable(new BitmapDrawable());

            // 设置点击popupwindow外屏幕其它地方消失
            pwStyle.setOutsideTouchable(true);

            // 设置缩放动画(默认从右上角开始)
            translateAnimation = new TranslateAnimation(0, 0, 1, 0, Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            translateAnimation.setInterpolator(new AccelerateInterpolator());
            translateAnimation.setDuration(500);

            // 设置商品的图片
            Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into((ImageView) pwStyleView.findViewById(R.id.iv_goods_cover));

            // 设置商品的价格
            TextView tv_goods_price = (TextView) pwStyleView.findViewById(R.id.tv_goods_price);
            tv_goods_price.setText("￥" + goodsBean.getCover_price());

            // 通过popupView获取其内部的按钮并分别设置监听
            pwStyleView.findViewById(R.id.iv_close_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwStyle.dismiss();
                }
            });
            pwStyleView.findViewById(R.id.tv_joinCart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GoodsInfoActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                    pwStyle.dismiss();
                }
            });

        }

        // 在重新显示之前，设置popupwindow的消失
        if (pwStyle.isShowing()) {
            pwStyle.dismiss();
        }

        // 设置popupWindow要挂载的位置,相对某个控件的位置（正左下方），无偏移
        pwStyle.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.activity_goods_info), Gravity.BOTTOM, 0, 0);
        pwStyle.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

        // 设置popupWindow弹出时的动画
        pwStyleView.startAnimation(translateAnimation);
    }

    // 弹出一个popupwindow
    private void showPopupWindow(View v) {

        if (popupWindow == null) {

            // 设置contentView
            popupView = LayoutInflater.from(GoodsInfoActivity.this).inflate(R.layout.item_popupwindow, null);
            popupWindow = new PopupWindow(popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setContentView(popupView);

//            popupView = View.inflate(GoodsInfoActivity.this, R.layout.item_popupwindow, null);
//            // 参数2,3：指明popupwindow的宽度和高度
//            popupWindow = new PopupWindow(popupView, DensityUtil.px2dip(this, 200), DensityUtil.px2dip(this, 225));

            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);

            // 设置缩放动画(默认从右上角开始)
            scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
            scaleAnimation.setDuration(500);

            // 通过popupView获取其内部的三个Linearlayout,并分别设置监听
            popupView.findViewById(R.id.tv_message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GoodsInfoActivity.this, "消息", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            });
            popupView.findViewById(R.id.tv_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GoodsInfoActivity.this, "主页", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            });
            popupView.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GoodsInfoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            });
            popupView.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShareView(); // 显示分享视图
                    popupWindow.dismiss();
                }
            });
        }

        // 在重新显示之前，设置popupwindow的消失
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // 设置popupWindow要挂载的位置,相对某个控件的位置（正左下方），无偏移
        popupWindow.showAsDropDown(v, 150, 10);

        // 设置popupWindow弹出时的动画
        popupView.startAnimation(scaleAnimation);
    }

    private void showShareView() {

        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("这个宝贝不错哦");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("这件宝贝不错哦!");
        // 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("心动商城");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");

        // 启动分享GUI
        oks.show(this);
    }

    private void toShoppingCartPager() {
//        Intent intent = new Intent(GoodsInfoActivity.this, ShoppingCartFragment.class);
//        startActivity(intent);
    }

}
