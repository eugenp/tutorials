package com.baeldung.yauaa;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Component
public class UserAgentAttributeLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(UserAgentAttributeLoggingFilter.class);
    private final UserAgentAnalyzer userAgentAnalyzer;

    public UserAgentAttributeLoggingFilter(UserAgentAnalyzer userAgentAnalyzer) {
        this.userAgentAnalyzer = userAgentAnalyzer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String userAgentString = request.getHeader("User-Agent");

        UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);
        userAgent
            .getAvailableFieldNamesSorted()
            .forEach(fieldName -> {
                log.info("{}: {}", fieldName, userAgent.getValue(fieldName));
            });

        filterChain.doFilter(request, response);
    }

}