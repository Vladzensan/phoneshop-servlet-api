package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private CartService cartService;

    private Cart cart;
    private CartItem cartItem;

    @Mock
    private Product product;

    @Mock
    private Product product1;


    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Before
    public void setUp() {
        cartService = HttpSessionCartService.getInstance();
        when(product.getStock()).thenReturn(20);
        when(product.getPrice()).thenReturn(BigDecimal.TEN);
        when(product1.getStock()).thenReturn(20);
        when(product1.getPrice()).thenReturn(BigDecimal.TEN);
        cart = new Cart();
        cartItem = new CartItem(product, 5);
        List<CartItem> list = new ArrayList<>();
        list.add(cartItem);
        cart.setCartItems(list);
    }

    @Test
    public void testGetCart() {
        when(request.getSession()).thenReturn(httpSession);
        cartService.getCart(request);
        verify(httpSession).setAttribute(anyString(), any(Cart.class));
    }

    @Test
    public void testAddExistingProduct() {
        int initItemCount = cart.getCartItems().size();
        cartService.add(cart, product, 10);
        assertSame(cartItem.getQuantity(), 15);
        assertEquals(initItemCount, cart.getCartItems().size());
    }

    @Test
    public void testAddNewProduct() {
        cartService.add(cart, product1, 10);
        assertEquals(cart.getCartItems().size(), 2);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddNoEnoughStock() {
        cartService.add(cart, product1, 30);
    }
}