package com.es.phoneshop.model.product;

import com.es.phoneshop.SortItems.SortItem;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static Long maxID = 0L;

    private List<Product> products;

    public static ArrayListProductDao getInstance() {
        return ProductDaoHolder.productDao;
    }

    private ArrayListProductDao() {
        maxID = 0L;
        products = new ArrayList<>();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Element with id " + id + " is not found"));
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortItem sortItem) {
        List<Product> result = products.stream()
                .filter(x -> x.getPrice() != null && x.getStock() > 0)
                .collect(Collectors.toList());
        return new ProductDaoQueryService().getQueryList(result, query, sortItem);
    }

    @Override
    public synchronized void save(Product product) {
        if (!containsID(product.getId())) {
            products.add(product);
        } else {
            throw new IllegalArgumentException("Product is already in the list");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.remove(getProduct(id));
    }


    private boolean containsID(Long id) {
        return products.stream()
                .anyMatch(product -> product.getId().equals(id));
    }

    private Long getNextId() {
        return ++maxID;
    }

    private static class ProductDaoHolder {
        static final ArrayListProductDao productDao = new ArrayListProductDao();
    }

}