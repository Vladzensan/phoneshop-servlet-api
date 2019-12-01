package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import static junit.framework.TestCase.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionWatchHistoryTest {

    private WatchService watchService;
    private WatchHistory watchHistory;

    @Mock
    Product product1;
    @Mock
    Product product2;
    @Mock
    Product product3;
    @Mock
    Product product4;

    @Before
    public void setUp() {
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product4.getId()).thenReturn(4L);

        watchService = HttpSessionWatchService.getInstance();
        watchHistory = new WatchHistory();
        LinkedList<Product> list = new LinkedList<>();
        list.add(product1);
        list.add(product2);
        watchHistory.setViewedProducts(list);
    }

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Test
    public void testGetWatchHistory() {
        when(request.getSession()).thenReturn(httpSession);
        watchService.getWatchHistory(request);
        verify(httpSession).setAttribute(anyString(), any(WatchHistory.class));
    }

    @Test
    public void testSetLastViewedNoOverload() {
        watchService.setLastViewed(watchHistory, product3);

        assertSame(watchHistory.getViewedProducts().getLast(), product3);
        assertSame(watchHistory.getViewedProducts().get(0), product1);
    }

    @Test
    public void testSetLastViewedOverload() {
        watchService.setLastViewed(watchHistory, product3);
        watchService.setLastViewed(watchHistory, product4);

        assertSame(watchHistory.getViewedProducts().getLast(), product4);
        assertSame(watchHistory.getViewedProducts().get(0), product2);
    }

}