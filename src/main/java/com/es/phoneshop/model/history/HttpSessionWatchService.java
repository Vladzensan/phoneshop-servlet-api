package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Deque;

public class HttpSessionWatchService implements WatchService {
    private static String HISTORY_ATTRIBUTE = HttpSessionWatchService.class + ".history";
    private static int QUANTITY_RECENT = 3;

    public static HttpSessionWatchService getInstance() {
        return WatchServiceHolder.watchService;
    }

    private HttpSessionWatchService() {

    }

    @Override
    public void setLastViewed(WatchHistory watchHistory, Product product) {
        Deque<Product> products = watchHistory.getViewedProducts();

        boolean alreadyInList = products.stream()
                .anyMatch(product1 -> product1.getId().equals(product.getId()));
        if (!alreadyInList) {
            products.addLast(product);
        }

        if (products.size() > QUANTITY_RECENT) {
            products.pollFirst();
        }
    }

    @Override
    public WatchHistory getWatchHistory(HttpServletRequest request) {
        WatchHistory watchHistory = (WatchHistory) request.getSession().getAttribute(HISTORY_ATTRIBUTE);
        if (watchHistory == null) {
            watchHistory = new WatchHistory();
            request.getSession().setAttribute(HISTORY_ATTRIBUTE, watchHistory);
        }
        return watchHistory;
    }

    private static class WatchServiceHolder {
        final static HttpSessionWatchService watchService = new HttpSessionWatchService();
    }
}
