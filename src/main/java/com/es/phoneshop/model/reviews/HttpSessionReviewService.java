package com.es.phoneshop.model.reviews;

import java.util.List;
import java.util.stream.Collectors;

public class HttpSessionReviewService implements ReviewService { // class is created for further review processing features
    private ReviewDao reviewDao;

    public static HttpSessionReviewService getInstance() {
        return ReviewServiceHolder.instance;
    }

    private HttpSessionReviewService() {
        reviewDao = ArrayListReviewDao.getInstance();
    }

    @Override
    public void postReview(Review review) {
        ReviewDao reviewDao = ArrayListReviewDao.getInstance();
        reviewDao.add(review);
    }

    @Override
    public List<Review> getReviewsForProduct(Long productId) {
        return reviewDao.getReviewList().stream()
                .filter(review -> review.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    private static class ReviewServiceHolder {
        final static HttpSessionReviewService instance = new HttpSessionReviewService();
    }
}
