package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] quantityStrings = request.getParameterValues("quantity");
        String[] productIds = request.getParameterValues("productId");

        Locale locale = request.getLocale();

        Cart cart = cartService.getCart(request);

        StringBuilder error = new StringBuilder();

        if (quantityStrings != null) {

            for (int i = 0; i < quantityStrings.length; i++) {
                Product product = productDao.getProduct(Long.valueOf(productIds[i]));
                try {
                    int quantity = getQuantityFromString(quantityStrings[i], locale);
                    cartService.update(cart, product, quantity);
                } catch (ParseException e) {
                    error.append("\n" + "Wrong quantity value (").append(product.getDescription()).append(")");
                } catch (OutOfStockException e) {
                    error.append("\n" + "Not enough stock. Available:").append(e.getAvailableStock()).append("(").append(product.getDescription()).append(")");
                }
            }

        }

        if (error.length() > 0) {
            request.setAttribute("error", error.toString());
            showPage(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?success=true");
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    private int getQuantityFromString(String quantityString, Locale locale) throws ParseException {
        return NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
    }
}