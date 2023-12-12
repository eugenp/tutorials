package com.baeldung.httpclient;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;

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
