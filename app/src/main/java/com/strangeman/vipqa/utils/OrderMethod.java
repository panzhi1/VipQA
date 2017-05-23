package com.strangeman.vipqa.utils;



import com.strangeman.vipqa.entity.Order;

import java.util.List;

/**
 * Created by panzhi on 2017/5/22.
 */

public class OrderMethod {
    private List<Order> orders;
    private List<Order> getOrders(){
        return  orders;
    }
    private void setOrders(List<Order> orders){
        this.orders=orders;
    }

}
