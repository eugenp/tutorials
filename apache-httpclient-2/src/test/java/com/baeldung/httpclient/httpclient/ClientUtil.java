package com.baeldung.httpclient.httpclient;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

public final class ClientUtil {

    private ClientUtil(){}

    public static void closeClient(CloseableHttpClient client) throws IOException {
        if (client == null) {
            return;
        }

        client.close();
    }
}
