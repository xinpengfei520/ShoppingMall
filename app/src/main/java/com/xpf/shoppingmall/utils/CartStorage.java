package com.xpf.shoppingmall.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.xpf.shoppingmall.MyApplication;
import com.xpf.shoppingmall.domain.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xpf on 2016/11/22 :)
 * Wechat:18091383534
 * Function:购物车存储类(单例)
 */

public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private final Context mContext;
    // 使用SparseArray的性能要好于HashMap
    private SparseArray<GoodsBean> sparseArray;

    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyApplication.getmContext());
        }
        return instance;
    }

    public CartStorage(Context mContext) {
        this.mContext = mContext;
        // 把之前存储到本地的数据给读取出来
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }

    // 从本地读取数据放到SparseArray集合中
    private void listToSparseArray() {

        List<GoodsBean> listGoodsBean = getAllGoods();
        // 把List数据转换成SparseArray
        for (int i = 0; i < listGoodsBean.size(); i++) {
            GoodsBean goodsBean = listGoodsBean.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        }
    }

    // 得到本地存储中所有的商品数据
    public List<GoodsBean> getAllGoods() {

        List<GoodsBean> goodsBeanList = new ArrayList<>();
        // 1.从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        // 2.解析为bean对象
        if (!TextUtils.isEmpty(json)) {
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeanList;
    }

    // 添加数据
    public void addData(GoodsBean goodsBean) {

        // 1.添加到内存中SparseArray
        // 如果当前数据已经存在，就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null) {
            // 内存中有了这条数据
            tempData.setNumber(tempData.getNumber() + 1);
        } else {
            tempData = goodsBean;
            tempData.setNumber(1);
        }

        // 同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()), tempData);

        // 2.同步到本地
        saveLocal();
    }

    // 保存数据到本地
    private void saveLocal() {
        // 1.SparseArray转换成List
        List<GoodsBean> goodsBeanList = sparseToList();
        // 2.使用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        // 3.把String数据保存
        CacheUtils.saveString(mContext, JSON_CART, json);
    }

    // parseArray转换成List
    private List<GoodsBean> sparseToList() {

        List<GoodsBean> goodsBeanList = new ArrayList<>();

        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }

    // 删除购物车数据
    public void deleteData(GoodsBean goodsBean) {

        // 1.从内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        // 2.把内存中的保存到本地
        saveLocal();
    }

    // 更新数据
    public void updateData(GoodsBean goodsBean) {
        // 1.内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);

        // 2.同步到本地
        saveLocal();
    }
}
