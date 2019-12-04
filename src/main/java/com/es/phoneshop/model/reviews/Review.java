package com.es.phoneshop.model.reviews;

public class Review {
    private String author;
    private int rating;
    private String comment;
    private Long productId;

    public Review(String author, int rating, String comment, Long productId) {
        this.author = author;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
