package com.baeldung.jersey.serverlogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomServerLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomServerLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LOG.info("Incoming request: {} {}", requestContext.getMethod(), requestContext.getUriInfo()
            .getRequestUri());
        LOG.info("Request headers: {}", requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        LOG.info("Outgoing response: {} {} - Status {}", requestContext.getMethod(), requestContext.getUriInfo()
            .getRequestUri(), responseContext.getStatus());
        LOG.info("Response headers: {}", responseContext.getHeaders());
    }
}
