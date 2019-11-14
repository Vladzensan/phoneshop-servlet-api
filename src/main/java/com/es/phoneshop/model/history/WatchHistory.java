package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;

public class WatchHistory {
    private LinkedList<Product> viewedProducts;

    public WatchHistory(){
        viewedProducts = new LinkedList<>();
    }

    public LinkedList<Product> getViewedProducts(){
        return viewedProducts;
    }
}
