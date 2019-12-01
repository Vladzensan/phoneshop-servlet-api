package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Product implements Serializable {

    private Long id;
    private String code;
    private String description;
    private Map<Date, BigDecimal> priceHistory;
    private Currency currency;
    private int stock;
    private String imageUrl;

    public Product() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Product product = (Product) obj;

        return id.equals(product.getId()) &&
                code.equals(product.getCode()) &&
                stock == product.getStock();
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        priceHistory = new LinkedHashMap<>();
        if (price != null) {
            priceHistory.put(new Date(), price);
        }
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        priceHistory.put(new Date(), price);
    }

    public BigDecimal getPrice() {
        return priceHistory.size() != 0
                ? (BigDecimal) priceHistory.values().toArray()[priceHistory.size() - 1]
                : null;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<Date, BigDecimal> getPriceHistory() {
        return priceHistory;
    }
}