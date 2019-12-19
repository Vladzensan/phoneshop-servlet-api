package com.es.phoneshop.web;

import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = getOrderId(request);
        Order order = orderService.getOrderById(orderId);

        request.setAttribute("order", order);

        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);

    }

    private String getOrderId(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.substring(uri.lastIndexOf('/') + 1);
    }
}
