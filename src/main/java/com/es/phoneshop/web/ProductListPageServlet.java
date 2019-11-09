package com.es.phoneshop.web;

import com.es.phoneshop.SortItems.LookupUtil;
import com.es.phoneshop.SortItems.SortItem;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("query");
        SortItem sortItem = LookupUtil.lookup(SortItem.class, request.getParameter("sort"));
        if (sortItem != null) {
            sortItem.order = LookupUtil.lookup(SortItem.Order.class, request.getParameter("order"));
        }
        request.setAttribute("products", productDao.findProducts(searchQuery, sortItem));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
