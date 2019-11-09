package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface WatchService {
    void setLastViewed(WatchHistory watchHistory, Product product);

    WatchHistory getWatchHistory(HttpServletRequest request);
}
