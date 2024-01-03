package com.baeldung.jersey.client.filter;

import java.io.IOException;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

public class AddHeaderOnRequestFilter implements ClientRequestFilter {

    public static final String FILTER_HEADER_VALUE = "filter-header-value";
    public static final String FILTER_HEADER_KEY = "x-filter-header";

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().add(FILTER_HEADER_KEY, FILTER_HEADER_VALUE);
    }
}
