package com.es.phoneshop.model.reviews;

import java.util.List;

public interface ReviewService {
    void postReview(Review review);

    List<Review> getReviewsForProduct(Long productId);
}
