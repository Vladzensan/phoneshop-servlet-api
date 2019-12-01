package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Product product, int quantity);

    void update(Cart cart, Product product, int newQuantity);

    void delete(Cart cart, Product product);
}
