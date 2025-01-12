package com.david.coupons.filters;

import com.david.coupons.utils.JWTUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String methodType = request.getMethod().toLowerCase();
        // Options means it's part of the CORS request
        // No need to check anything
        if (methodType.equals("options")){
            // filterChain.doFilter = All good, u may go ahead
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String url = request.getRequestURI().toLowerCase();
        if (isRequestAWhitelisted(methodType, url)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getHeader("Authorization");

        // Questions -
        // 1. Performance ??
        // 2. Error object ??
        // 3. Hacking ??
        try {
            JWTUtils.validateToken(token);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
//            e.printStackTrace(); ???
            response.setStatus(401);
        }
    }

    private boolean isRequestAWhitelisted(String methodType, String url) {
        if (methodType.equals("post") && url.endsWith("users/login")){
            return true;
        }

        if (methodType.equals("post") && url.endsWith("/users")){
            return true;
        }

        if (methodType.equals("post") && url.endsWith("/customers")){
            return true;
        }

        if (methodType.equals("get") && url.endsWith("/coupons")){
            return true;
        }

        if (methodType.equals("get") && url.matches(".*/coupons/\\d+$")) {
            return true;
        }

        if (methodType.equals("get") && url.matches("/coupons/bycompanyid")) {
            return true;
        }
        if (methodType.equals("get") && url.matches("/coupons/bymaxprice")) {
            return true;
        }

        if (methodType.equalsIgnoreCase("get") && url.matches("/companies")) {
            return true;
        }

        if (methodType.equalsIgnoreCase("get") && url.matches(".*/companies/\\d+$")) {
            return true;
        }

        if (methodType.equalsIgnoreCase("get") && url.matches(".*/categories/\\d+$")) {
            return true;
        }
        if (methodType.equals("get") && url.matches("/categories")) {
            return true;
        }

        if (methodType.equals("get") && url.endsWith("/verification")){
            return true;
        }



        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
