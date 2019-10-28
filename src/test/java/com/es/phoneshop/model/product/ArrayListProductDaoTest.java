package com.es.phoneshop.model.product;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class ArrayListProductDaoTest {

    private ProductDao productDao;

    public ArrayListProductDaoTest() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testGetProduct() {
        Product product = new Product("Test", "", new BigDecimal(4), Currency.getInstance("USD"), 4, "");
        productDao.save(product);
        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void testGetNonExistingProduct() {
        productDao.getProduct(Long.valueOf(-1));
    }

    @Test
    public void testFindProducts() {
        List<Product> products = productDao.findProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        boolean sizesEqual = products.stream().
                filter(x -> x.getStock() > 0 && x.getPrice() != null).
                collect(Collectors.toList()).size() == products.size();
        assertTrue(sizesEqual);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product("Test", "", new BigDecimal(4), Currency.getInstance("USD"), 4, "");
        productDao.save(product);
        Product product1 = productDao.getProduct(product.getId());
        assertNotNull(product1);
        assertEquals(product, product1);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteProduct() {
        Product product = new Product("Test", "", new BigDecimal(4), Currency.getInstance("USD"), 4, "");
        productDao.save(product);
        productDao.delete(product.getId());
        productDao.getProduct(product.getId());
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteNonExistingProduct() {
        productDao.delete(-1L);
    }


}
