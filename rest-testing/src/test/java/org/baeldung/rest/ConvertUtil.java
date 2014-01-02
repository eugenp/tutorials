package org.baeldung.rest;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

public class ConvertUtil {

    public static <T> String convertResourceToJson(final T resource) throws IOException {
        Preconditions.checkNotNull(resource);

        return new ObjectMapper().writeValueAsString(resource);
    }

    public static <T> T convertJsonToResource(final String json, final Class<T> clazzOfResource) throws IOException {
        Preconditions.checkNotNull(json);
        Preconditions.checkNotNull(clazzOfResource);

        return new ObjectMapper().readValue(json, clazzOfResource);
    }

}
