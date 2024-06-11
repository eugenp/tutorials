package com.baeldung.responsebody;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;

@Component
public class MD5Filter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, responseCacheWrapperObject);

        byte[] responseBody = responseCacheWrapperObject.getContentAsByteArray();

        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] md5Hash = md5Digest.digest(responseBody);
            String md5HashString = DatatypeConverter.printHexBinary(md5Hash);
            responseCacheWrapperObject.setHeader("Response-Body-MD5", md5HashString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        responseCacheWrapperObject.copyBodyToResponse();
    }
}