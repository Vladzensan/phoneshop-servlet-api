package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(CartItem cartItem){
        this.product = cartItem.product;
        this.quantity = cartItem.quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProdID: " + product.getId() + " Qty: " + quantity;
    }
}