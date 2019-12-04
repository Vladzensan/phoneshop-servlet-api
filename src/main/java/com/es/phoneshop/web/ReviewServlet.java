package com.es.phoneshop.web;

import com.es.phoneshop.model.reviews.HttpSessionReviewService;
import com.es.phoneshop.model.reviews.Review;
import com.es.phoneshop.model.reviews.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReviewServlet extends HttpServlet {
    private ReviewService reviewService = HttpSessionReviewService.getInstance();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String author = (String) request.getParameter("author");
        int rating = Integer.parseInt((String) request.getParameter("rating"));
        String comment = (String) request.getParameter("comment");

        Long productId = getProductIdFromRequest(request);
        Review review = new Review(author, rating, comment, productId);

        reviewService.postReview(review);

        String targetUri = request.getRequestURI().replace("review/", "");
        response.sendRedirect(targetUri);

    }

    private Long getProductIdFromRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String idStr = uri.substring(uri.lastIndexOf("/") + 1);

        return Long.parseLong(idStr);
    }
}
