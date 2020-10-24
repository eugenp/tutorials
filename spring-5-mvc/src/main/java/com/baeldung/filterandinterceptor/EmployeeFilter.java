package com.baeldung.filterandinterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class EmployeeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        logger.info("Inside The Filter !!");
        logger.info("Host Name : " + httpServletRequest.getLocalName());
        logger.info("Port Number : " + httpServletRequest.getLocalPort());
        logger.info("Method : " + httpServletRequest.getMethod());
        logger.info("URI : " + httpServletRequest.getRequestURI());
        chain.doFilter(request, response);
        logger.info("Response Status : " + httpServletResponse.getStatus());
        logger.info("Exiting The Filter !!");
    }

}
