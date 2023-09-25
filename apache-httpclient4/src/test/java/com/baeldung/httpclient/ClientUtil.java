package com.baeldung.httpclient;

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;

public final class ClientUtil {

    private ClientUtil(){}

    public static void closeClient(CloseableHttpClient client) throws IOException {
        if (client == null) {
            return;
        }

        client.close();
    }
}
