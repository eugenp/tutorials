package com.baeldung.jersey.server.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
