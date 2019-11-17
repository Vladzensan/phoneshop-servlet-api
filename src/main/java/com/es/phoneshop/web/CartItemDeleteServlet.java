package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long productID = Long.valueOf(request.getParameter("productId"));
        Product product = productDao.getProduct(productID);
        cartService.delete(cartService.getCart(request), product);
        response.sendRedirect(request.getContextPath() + "/cart?success=true");
    }
}
