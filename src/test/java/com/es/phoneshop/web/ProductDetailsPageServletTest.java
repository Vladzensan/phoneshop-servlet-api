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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
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
    @Mock
    private HttpSession session;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(null);

        when(product1.getId()).thenReturn(0L);
        when(product2.getId()).thenReturn(1L);
        when(product3.getId()).thenReturn(2L);
        when(product3.getStock()).thenReturn(5);
        when(product3.getPrice()).thenReturn(BigDecimal.TEN);
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>(Arrays.asList(product1, product2, product3)));
        servlet.init();
    }

    @Test
    public void testDoGetProductNotFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/" + Long.MAX_VALUE);
        servlet.doGet(request, response);

        verify(response).sendError(eq(404), anyString());
    }

    @Test
    public void testDoGetProductFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/2");
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("viewedProducts"), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(request).setAttribute(eq("product"), eq(product3));
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostProductFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/2");
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostProductNotFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/2");
        when(request.getParameter("quantity")).thenReturn("wrongValue");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(requestDispatcher).forward(request, response);
    }


}