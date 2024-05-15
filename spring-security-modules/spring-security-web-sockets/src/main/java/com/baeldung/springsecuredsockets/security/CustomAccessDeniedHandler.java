package com.baeldung.springsecuredsockets.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Applies to User Roles - not to login failures or unauthenticaed access attempts.
 */

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.sendRedirect(request.getContextPath() + "/denied");
    }
}