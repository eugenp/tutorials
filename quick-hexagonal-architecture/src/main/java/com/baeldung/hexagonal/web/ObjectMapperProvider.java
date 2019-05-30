package com.baeldung.hexagonal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/** Registers the application {@link ObjectMapper} as the JAX-RS provider for application/json */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    @Inject
    public ObjectMapperProvider(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
        return mapper;
    }
}
