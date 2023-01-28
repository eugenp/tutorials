package com.baeldung.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

public final class ResponseUtil {
    private ResponseUtil() {
    }

    public static void closeResponse(CloseableHttpResponse response) throws IOException {
        if (response == null) {
            return;
        }

        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                entity.getContent().close();
            }
        } finally {
            response.close();
        }
    }
}
