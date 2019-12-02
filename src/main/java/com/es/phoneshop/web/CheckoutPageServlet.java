package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.customerInfo.CustomerDetails;
import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() {
        orderService = DefaultOrderService.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        Map<String, String> errors = new HashMap<>();
        CustomerDetails customerDetails = getDetailsFromRequest(request, errors);

        request.setAttribute("order", order);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
        } else {
            order.setCustomerDetails(customerDetails);
            String secureId = orderService.placeOrder(order);
            cartService.clearCart(cartService.getCart(request));
            response.sendRedirect(request.getContextPath() + "/orderoverview/" + secureId);
        }
    }

    private CustomerDetails getDetailsFromRequest(HttpServletRequest request, Map<String, String> errors) {
        String surname = extractParameter("Surname", request, errors);
        String name = extractParameter("Name", request, errors);
        String phone = extractParameter("Phone", request, errors);
        String address = extractParameter("Address", request, errors);
        String date = extractParameter("Date", request, errors);
        String paymentStr = extractParameter("Payment", request, errors);
        CustomerDetails.PaymentMethod paymentMethod = null;
        if (paymentStr != null && !paymentStr.isEmpty()) {
            paymentMethod = CustomerDetails.PaymentMethod.valueOf(paymentStr);
        }

        return new CustomerDetails(surname, name, phone, address, paymentMethod);
    }

    private String extractParameter(String paramName, HttpServletRequest request, Map<String, String> errors) {
        String param = request.getParameter(paramName);

        if (param.isEmpty() || param == null) {
            errors.put(paramName, paramName + " is required");
        }

        return param;
    }
}