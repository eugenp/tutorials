package com.baeldung.jersey.client.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RequestClientFilter implements ClientRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestClientFilter.class);

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        LOG.info("Request client filter");

        requestContext.setProperty("test", "test client request filter");
    }

}
