package com.es.phoneshop.SortItems;

public enum SortItem {
    price, description;
    public Order order = Order.asc;

    public enum Order {
        asc, desc
    }
}
