package com.baeldung.mime;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.baeldung.mime.Foo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonMarshaller implements IMarshaller {
    private final Logger logger = LoggerFactory.getLogger(JacksonMarshaller.class);

    private final ObjectMapper objectMapper;

    public JacksonMarshaller() {
        super();

        objectMapper = new ObjectMapper();
    }

    // API

    @Override
    public final <T> String encode(final T resource) {
        String entityAsJSON = null;
        try {
            entityAsJSON = objectMapper.writeValueAsString(resource);
        } catch (final IOException ioEx) {
            logger.error("", ioEx);
        }

        return entityAsJSON;
    }

    @Override
    public final <T> T decode(final String resourceAsString, final Class<T> clazz) {
        T entity = null;
        try {
            entity = objectMapper.readValue(resourceAsString, clazz);
        } catch (final IOException ioEx) {
            logger.error("", ioEx);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <T> List<T> decodeList(final String resourcesAsString, final Class<T> clazz) {
        List<T> entities = null;
        try {
            if (clazz.equals(Foo.class)) {
                entities = objectMapper.readValue(resourcesAsString, new TypeReference<List<T>>() {
                    // ...
                });
            } else {
                entities = objectMapper.readValue(resourcesAsString, List.class);
            }
        } catch (final IOException ioEx) {
            logger.error("", ioEx);
        }

        return entities;
    }

    @Override
    public final String getMime() {
        return MediaType.APPLICATION_JSON.toString();
    }

}
