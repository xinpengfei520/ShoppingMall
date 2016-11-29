package com.atguigu.shoppingmall.domain;

import java.io.Serializable;

/**
 * 作用：商品信息的Bean类
 */
public class GoodsBean implements Serializable {

    private int number = 1;
    private String name;        // 名称
    private String figure;      // 图片
    private String product_id;  // 产品id
    private String cover_price; // 价格
    private boolean isSelected = true; // 是否被选中,默认选中

    public GoodsBean() {
    }

    public GoodsBean(int number, String name, String figure, String product_id,
                     String cover_price, boolean isSelected) {
        this.number = number;
        this.name = name;
        this.figure = figure;
        this.product_id = product_id;
        this.cover_price = cover_price;
        this.isSelected = isSelected;
    }

    public GoodsBean(String name, String cover_price, String figure, String product_id) {
        this.name = name;
        this.figure = figure;
        this.product_id = product_id;
        this.cover_price = cover_price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", isSelected=" + isSelected +
                '}';
    }
}
