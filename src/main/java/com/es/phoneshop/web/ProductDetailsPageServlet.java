package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.history.HttpSessionWatchService;
import com.es.phoneshop.model.history.WatchHistory;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.reviews.HttpSessionReviewService;
import com.es.phoneshop.model.reviews.Review;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private HttpSessionWatchService watchService;
    private HttpSessionReviewService reviewService;

    public void init() {
        this.productDao = ArrayListProductDao.getInstance();
        this.cartService = HttpSessionCartService.getInstance();
        this.watchService = HttpSessionWatchService.getInstance();
        this.reviewService = HttpSessionReviewService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long productID = getProductID(request);
            Product product = productDao.getProduct(productID);

            List<Review> reviews = reviewService.getReviewsForProduct(productID);

            request.setAttribute("reviews", reviews);

            WatchHistory watchHistory = watchService.getWatchHistory(request);
            watchService.setLastViewed(watchHistory, product);

            showPage(product, request, response);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            response.sendError(404, e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productID = getProductID(request);
        Product product = productDao.getProduct(productID);
        String error = null;
        try {
            int quantity = getQuantity(request);

            Cart cart = cartService.getCart(request);
            cartService.add(cart, product, quantity);
        } catch (ParseException e) {
            error = "Not a number";
        } catch (OutOfStockException e) {
            error = "Not enough stock. Available:" + e.getAvailableStock();
        }

        if (error != null) {
            request.setAttribute("error", error);
            showPage(product, request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?success=true");
        }
    }

    private long getProductID(HttpServletRequest request) {
        String URI = request.getRequestURI();
        return Long.valueOf(URI.substring(URI.lastIndexOf("/") + 1));
    }

    private int getQuantity(HttpServletRequest request) throws ParseException {
        String quantityString = request.getParameter("quantity");
        Locale locale = request.getLocale();

        return NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
    }

    private void showPage(Product product, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", product);
        request.setAttribute("viewedProducts", watchService.getWatchHistory(request).getViewedProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
}