package com.travelapp;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RedirectManager implements Filter {


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        if (path.startsWith("/api") || path.contains(".")) {
//            System.out.println("to server:" + path);
            chain.doFilter(request, response);
            return;
        }
//        System.out.println("to front:" + path);
        request.getRequestDispatcher("/").forward(request, response);
    }

}