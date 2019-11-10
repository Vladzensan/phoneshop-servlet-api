package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        when(product1.getId()).thenReturn(0L);
        when(product2.getId()).thenReturn(1L);
        when(product3.getId()).thenReturn(2L);
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>(Arrays.asList(product1, product2, product3)));
    }

    @Test
    public void testDoGetProductNotFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/" + Long.MAX_VALUE);
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/" + 0);
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("product"), eq(product1));
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}