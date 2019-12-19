package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

    public static DefaultOrderService getInstance() {
        return OrderServiceHolder.orderService;
    }

    private DefaultOrderService() {

    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order(cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList())
        );
        BigDecimal deliveryCost = getDeliveryCost();

        order.setDeliveryCost(deliveryCost);
        order.setSubTotal(cart.getTotalCost());
        order.setTotalCost(cart.getTotalCost().add(deliveryCost));

        return order;
    }


    @Override
    public String placeOrder(Order order) {
        String secureId = UUID.randomUUID().toString();
        order.setSecureId(secureId);
        OrderDao orderDao = ArrayListOrderDao.getInstance();
        orderDao.saveOrder(order);
        return secureId;
    }


    @Override
    public Order getOrderById(String secureId) {
        return ArrayListOrderDao.getInstance().getOrder(secureId);

    }

    private BigDecimal getDeliveryCost() {
        return BigDecimal.TEN;
    }

    private static class OrderServiceHolder {
        final static DefaultOrderService orderService = new DefaultOrderService();
    }
}
