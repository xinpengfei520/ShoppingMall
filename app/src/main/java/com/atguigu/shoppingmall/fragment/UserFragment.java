package com.atguigu.shoppingmall.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.activity.LoginActivity;
import com.atguigu.shoppingmall.activity.MessageCenterActivity;
import com.atguigu.shoppingmall.activity.MyOrderActivity;
import com.atguigu.shoppingmall.activity.SettingActivity;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.utils.BitmapUtils;
import com.atguigu.shoppingmall.view.MyScrollView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:用户页面的fragment
 */

public class UserFragment extends BaseFragment {

    @Bind(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_all_order)
    TextView tvAllOrder;
    @Bind(R.id.tv_user_pay)
    TextView tvUserPay;
    @Bind(R.id.tv_user_receive)
    TextView tvUserReceive;
    @Bind(R.id.tv_user_finish)
    TextView tvUserFinish;
    @Bind(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @Bind(R.id.tv_user_location)
    TextView tvUserLocation;
    @Bind(R.id.tv_user_collect)
    TextView tvUserCollect;
    @Bind(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @Bind(R.id.tv_user_score)
    TextView tvUserScore;
    @Bind(R.id.tv_user_prize)
    TextView tvUserPrize;
    @Bind(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @Bind(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @Bind(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @Bind(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @Bind(R.id.scrollview)
    MyScrollView scrollview;
    @Bind(R.id.tv_usercenter)
    TextView tvUsercenter;
    @Bind(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @Bind(R.id.ib_user_message)
    ImageButton ibUserMessage;
    private int requestCode = 1;

    @Override
    public View initView() {

        Log.e("TAG", "用户面的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.bind(this, view);
        tvUsercenter.setAlpha(0);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "用户面的数据被初始化了");

        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;

                    case MotionEvent.ACTION_MOVE: // 下滑是正，上滑是负
                        // 初始状态为125,即最大值是125，全部显示不透明是(40?)
                        ibUserIconAvator.getLocationOnScreen(location);
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.ib_user_icon_avator, R.id.ib_user_setting, R.id.ib_user_message, R.id.tv_all_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_user_icon_avator:
//                startActivity(new Intent(mContext, LoginActivity.class));
                startActivityForResult(new Intent(mContext, LoginActivity.class), requestCode);
                break;
            case R.id.ib_user_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.ib_user_message:
                startActivity(new Intent(mContext, MessageCenterActivity.class));
                break;
            case R.id.tv_all_order:
                startActivity(new Intent(mContext, MyOrderActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String screen_name = data.getStringExtra("screen_name");
            String profile_image_url = data.getStringExtra("profile_image_url");

            // 设置登录的用户头像
            Picasso.with(mContext).load(profile_image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    // 先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                    Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                    // 对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle(); // 必须更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(ibUserIconAvator);

            // 设置登录的用户名
            tvUsername.setText(screen_name);
        } else {
            // 设置登录的用户名
            tvUsername.setText("已登录");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
