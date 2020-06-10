package com.baeldung.loginredirect;

import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class LoginPageFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isAuthenticated() && ((HttpServletRequest) request).getRequestURI().equals("/loginUser")) {

            String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
              ((HttpServletRequest) request).getContextPath() + "/userMainPage");

            ((HttpServletResponse) response).setStatus(HttpStatus.SC_TEMPORARY_REDIRECT);
            ((HttpServletResponse) response).setHeader("Location", encodedRedirectURL);
        }
        chain.doFilter(request, response);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}