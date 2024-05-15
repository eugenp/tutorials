package com.baeldung.sentry.support;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import io.sentry.Sentry;
import io.sentry.SentryLevel;

@WebFilter(urlPatterns = "/*")
public class SentryFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
            int rc = ((HttpServletResponse) response).getStatus();
            if (rc / 100 == 5) {
                Sentry.captureMessage("Application error: code=" + rc, SentryLevel.ERROR);
            }
        } catch (Throwable t) {
            Sentry.captureException(t);
            throw t;
        }
    }
}
