package org.baeldung.security.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class CustomHttpFilter extends GenericCustomHttpFilter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println(config.getInitParameter("first-init-param") +
                config.getInitParameter("second-init-param"));
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain
            chain) throws IOException, ServletException {
        System.out.println("CustomHttpFilter is in action [the request is going to the server].");
        chain.doFilter(request, response);
        System.out.println("CustomHttpFilter is in action [the request has come from the server].");
    }

}