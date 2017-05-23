package com.strangeman.vipqa.entity;

/**
 * Created by panzhi on 2017/5/17.
 */


public class Order {
    private String orderId;
    private String userId;
    private String productId;
    private String orderDate;
    private int count;

    public Order(String orderId, String userId, String productId, String orderDate, int count) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.count = count;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getCount() {
        return count;
    }
}

