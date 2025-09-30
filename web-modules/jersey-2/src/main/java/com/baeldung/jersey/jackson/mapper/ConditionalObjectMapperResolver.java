package com.baeldung.jersey.jackson.mapper;

import com.baeldung.jersey.jackson.annotation.InternalApi;
import com.baeldung.jersey.jackson.annotation.PublicApi;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;


@Provider
public class ConditionalObjectMapperResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper publicApiMapper;
    private final ObjectMapper internalApiMapper;
    private final ObjectMapper defaultMapper;


    public ConditionalObjectMapperResolver() {
        publicApiMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
        publicApiMapper.enable(SerializationFeature.INDENT_OUTPUT);
        publicApiMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        publicApiMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        publicApiMapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);


        internalApiMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
        internalApiMapper.enable(SerializationFeature.INDENT_OUTPUT);
        internalApiMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        internalApiMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        internalApiMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);


        defaultMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
        defaultMapper.enable(SerializationFeature.INDENT_OUTPUT);
        defaultMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    public ObjectMapper getContext(Class<?> type) {
        if (isPublicApiModel(type)) return publicApiMapper;
        else if (isInternalApiModel(type)) return internalApiMapper;
        return defaultMapper;
    }


    private boolean isPublicApiModel(Class<?> type) {
        return type.getPackage().getName().contains("public.api") ||
            type.isAnnotationPresent(PublicApi.class);
    }


    private boolean isInternalApiModel(Class<?> type) {
        return type.getPackage().getName().contains("internal.api") ||
            type.isAnnotationPresent(InternalApi.class);
    }
}