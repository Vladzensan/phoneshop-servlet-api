package com.es.phoneshop.model.product;

import com.es.phoneshop.SortItems.SortItem;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts(String query, SortItem sortItem);

    void save(Product product);

    void delete(Long id);
}
