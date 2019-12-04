package com.es.phoneshop.model.reviews;

import java.util.List;

public interface ReviewDao {
    void add(Review review);

    List<Review> getReviewList();
}
