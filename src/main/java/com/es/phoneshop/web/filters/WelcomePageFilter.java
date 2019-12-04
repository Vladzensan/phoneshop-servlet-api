package com.es.phoneshop.web.filters;

import com.es.phoneshop.filterUtils.DefaultVisitorService;
import com.es.phoneshop.filterUtils.VisitorService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomePageFilter implements Filter {

    private VisitorService visitorService;

    @Override
    public void init(FilterConfig filterConfig) {
        visitorService = DefaultVisitorService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(visitorService.isPresentVisitor((HttpServletRequest) request)){
            chain.doFilter(request,response);
        } else{
            String link = ((HttpServletRequest) request).getRequestURI();
            ((HttpServletRequest) request).getSession().setAttribute("link", link);
            //((HttpServletResponse)response).sendRedirect( "/welcome");
            request.getRequestDispatcher("/WEB-INF/pages/welcome.jsp").forward(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
