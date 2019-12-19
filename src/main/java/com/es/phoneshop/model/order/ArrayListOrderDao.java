package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListOrderDao implements OrderDao {

    private List<Order> orders = new ArrayList<>();

    public static ArrayListOrderDao getInstance() {
        return OrderDaoHolder.orderDao;
    }

    private ArrayListOrderDao() {

    }

    @Override
    public void saveOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
        } else {
            throw new IllegalArgumentException("Order is already in the list");
        }

    }

    @Override
    public Order getOrder(String secureId) {
        return orders.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Order with id " + secureId + " not found"));
    }

    private static class OrderDaoHolder {
        final static ArrayListOrderDao orderDao = new ArrayListOrderDao();
    }
}
