package com.es.phoneshop.exceptions;

public class OutOfStockException extends RuntimeException {
    private int availableStock;

    public OutOfStockException(final int availableStock) {
        this.availableStock = availableStock;
    }

    public int getAvailableStock() {
        return this.availableStock;
    }
}