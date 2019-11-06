package com.es.phoneshop.model.product;

import com.es.phoneshop.SortItems.SortItem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class ProductDaoQueryService {

    public List<Product> getQueryList(List<Product> products, String query, SortItem sortField) {
        List<Product> processedList = search(products, query);
        if (sortField != null) {
            processedList = processedList.stream()
                    .sorted(getSortComparator(sortField))
                    .collect(Collectors.toList());
        }
        return processedList;
    }

    public List<Product> search(List<Product> products, String query) {
        if (query == null || query.isEmpty()) {
            return products;
        } else {
            String[] queryWords = query.toLowerCase().split("\\s+");
            return products.stream()
                    .collect(Collectors.toMap(product -> product, product -> Arrays.stream(queryWords)
                            .filter(queryWord -> product.getDescription().toLowerCase().contains(queryWord))
                            .count()))
                    .entrySet().stream()
                    .filter(map -> map.getValue() > 0)
                    .sorted(comparing(Map.Entry<Product, Long>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    private Comparator<Product> getSortComparator(SortItem sortItem) {
        Comparator<Product> comparator = null;
        switch (sortItem) {
            case price:
                comparator = comparing(Product::getPrice);
                break;
            case description:
                comparator = comparing(Product::getDescription);
                break;
        }
        if (sortItem.order == SortItem.Order.desc) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

}