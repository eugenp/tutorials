package com.baeldung.jersey.server.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jersey.server.config.HelloBinding;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
@HelloBinding
public class RestrictedOperationsRequestFilter implements ContainerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RestrictedOperationsRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        LOG.info("Restricted operations filter");
        if (ctx.getLanguage() != null && "EN".equals(ctx.getLanguage()
            .getLanguage())) {
            LOG.info("Aborting request");
            ctx.abortWith(Response.status(Response.Status.FORBIDDEN)
                .entity("Cannot access")
                .build());
        }

    }
}
