package com.baeldung.patterns.intercepting.filter.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class TemplateFilter extends BaseFilter {
    protected abstract void preFilter(
      HttpServletRequest request,
      HttpServletResponse response
    ) throws IOException, ServletException;

    protected abstract void postFilter(
      HttpServletRequest request,
      HttpServletResponse response
    ) throws IOException, ServletException;

    @Override
    public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        preFilter(httpServletRequest, httpServletResponse);
        chain.doFilter(request, response);
        postFilter(httpServletRequest, httpServletResponse);
    }
}
