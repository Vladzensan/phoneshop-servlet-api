package com.es.phoneshop.model.reviews;

import java.util.ArrayList;
import java.util.List;

public class ArrayListReviewDao implements ReviewDao {
    private List<Review> reviews;

    public static ArrayListReviewDao getInstance(){
        return ReviewDaoHolder.instance;
    }

    private ArrayListReviewDao(){
        reviews = new ArrayList<>();
    }
    @Override
    public void add(Review review) {
        reviews.add(review);
    }

    @Override
    public List<Review> getReviewList() {
        return reviews;
    }

    private static class ReviewDaoHolder{
        final static ArrayListReviewDao instance = new ArrayListReviewDao();
    }
}
