package com.xpf.shoppingmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xpf.shoppingmall.R;
import com.xpf.shoppingmall.activity.GoodsInfoActivity;
import com.xpf.shoppingmall.domain.GoodsBean;
import com.xpf.shoppingmall.domain.ResultBeanData;
import com.xpf.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xpf on 2016/11/21 :)
 * Wechat:18091383534
 * Function:主页面数据的RecyclerView的适配器
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int BANNER = 0;   // 广告类型
    public static final int CHANNEL = 1;  // 频道类型
    public static final int ACT = 2;      // 活动类型
    public static final int SECKILL = 3;  // 秒杀类型
    public static final int RECOMMEND = 4;// 推荐类型
    public static final int HOT = 5;      // 热卖类型
    private static final String GOODS_BEAN = "goods_bean";
    private Context mContext;
    private ResultBeanData.ResultBean resultBean;
    private int currentType = 0; // 默认为0
    private LayoutInflater mLayoutInflater; // 初始化布局加载器
    private long dt = 0; // 相差多少时间(毫秒)

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于getView()创建viewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("TAG", "onCreateViewHolder()==viewType==" + viewType);
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_act, null));
        } else if (viewType == SECKILL) {
            return new SecKillViewHolder(mContext, mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }


    /**
     * 相当于getView()方法中的绑定数据的模块
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SecKillViewHolder secKillViewHolder = (SecKillViewHolder) holder;
            secKillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }


    // 得到类型
    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    // 总共有多少条item 0-->6
    @Override
    public int getItemCount() {
        return 6;
    }

    /**
     * 热卖商品的ViewHolder
     */
    class HotViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotGridViewAdapter adapter;

        public HotViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            // 1.有数据了
            adapter = new HotGridViewAdapter(mContext, hot_info);
            // 2.设置适配器
            gv_hot.setAdapter(adapter);

            // 设置热卖item的点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "position===0" + position, Toast.LENGTH_SHORT).show();
                    // 热卖商品的bean类
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    // 商品的bean类
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    // 携带数据到商品详情页面
    public void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN, goodsBean);
        mContext.startActivity(intent);
    }

    /**
     * 推荐内容的ViewHolder
     */
    class RecommendViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private GridView gv_recommend;
        private TextView tv_more_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            // 1.有数据了

            // 2.设置适配器
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gv_recommend.setAdapter(adapter);

            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "position===" + position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);

                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    /**
     * 秒杀数据的ViewHolder
     */
    class SecKillViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SecKillRecyclerViewAdapter adapter;
        private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                dt -= 100;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:S");
                String time = dateFormat.format(new Date(dt));
                tv_time_seckill.setText(time);

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 100);
                if (dt <= 0) {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public SecKillViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            // 1.得到数据了
            // 2.设置数据
            adapter = new SecKillRecyclerViewAdapter(mContext, seckill_info.getList());
            rv_seckill.setAdapter(adapter);

            // 设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            // 设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SecKillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(listBean.getName());
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });

            // 设置秒杀倒计时
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());

            handler.sendEmptyMessageDelayed(0, 100);
        }
    }

    // 活动类型的ViewHolder
    class ActViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private ViewPager act_viewPager;

        public ActViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            act_viewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            act_viewPager.setPageMargin(20);
            act_viewPager.setOffscreenPageLimit(3);//>=3

            // setPageTransformer 决定动画效果(banner和zhy库里都有此效果)
            act_viewPager.setPageTransformer(true, new ScaleInTransformer());
            // 得到数据了
            // 设置act_info的适配器
            act_viewPager.setAdapter(new PagerAdapter() {

                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    // 添加到容器里面
                    container.addView(imageView);

                    // 设置点击事件
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "position===" + position, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

        }
    }

    /**
     * 频道信息的ViewHolder
     */
    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;

        public ChannelViewHolder(final Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);

            // 设置item的点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            // 得到数据了
            // 设置GridView的适配器
            adapter = new ChannelAdapter(mContext, channel_info);
            gv_channel.setAdapter(adapter);
        }
    }

    /**
     * Banner广告条幅的ViewHolder
     */
    class BannerViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            List<String> imageUrls = new ArrayList<>();
            // 得到图片地址的集合
            for (int i = 0; i < banner_info.size(); i++) {
                String imageUrl = banner_info.get(i).getImage();
                imageUrls.add(imageUrl);
            }

            // 设置banner的循环指示点的样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            // 设置BANNER的切换的样式
            banner.setBannerAnimation(Transformer.DepthPage);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    // 联网请求图片
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + path).into(imageView);
                }
            });

            banner.setImages(imageUrls);

            // 设置banner的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    mContext.startActivity(new Intent(mContext, GoodsInfoActivity.class));
                }
            });

            banner.start();
        }
    }
}
