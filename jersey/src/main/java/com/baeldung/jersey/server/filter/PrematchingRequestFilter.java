package com.baeldung.jersey.server.filter;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
public class PrematchingRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(PrematchingRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        LOG.info("prematching filter");
        if (ctx.getMethod()
            .equals("DELETE")) {
            LOG.info("\"Deleting request");
        }
    }
}
