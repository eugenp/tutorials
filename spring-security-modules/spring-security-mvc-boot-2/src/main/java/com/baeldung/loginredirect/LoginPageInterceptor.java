package com.baeldung.loginredirect;

import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class LoginPageInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        UrlPathHelper urlPathHelper = new UrlPathHelper();
        if (urlPathHelper.getLookupPathForRequest(request).equals("/loginUser") && isAuthenticated()) {

            String encodedRedirectURL = response.encodeRedirectURL(
              request.getContextPath() + "/userMainPage");
            response.setStatus(HttpStatus.SC_TEMPORARY_REDIRECT);
            response.setHeader("Location", encodedRedirectURL);

            return false;
        } else {
            return true;
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
