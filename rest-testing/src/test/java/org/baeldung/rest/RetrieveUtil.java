package org.baeldung.rest;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.google.common.base.Preconditions;

public class RetrieveUtil {

    public static String retrieveJsonFromResponse(final HttpResponse response) throws IOException {
        Preconditions.checkNotNull(response);

        return IOUtils.toString(response.getEntity().getContent());
    }

    public static <T> T retrieveResourceFromResponse(final HttpResponse response, final Class<T> clazz) throws IOException {
        Preconditions.checkNotNull(response);
        Preconditions.checkNotNull(clazz);

        final String jsonFromResponse = retrieveJsonFromResponse(response);
        return ConvertUtil.convertJsonToResource(jsonFromResponse, clazz);
    }

}
