package com.strangeman.vipqa.utils;



import com.strangeman.vipqa.entity.Product;

import java.util.List;

public class ProductMethod {
    private List<Product> products;

    public List<Product> getProducts(){
        return products;
    }
    public void setProducts(List<Product> products){
        this.products=products;
    }
}
