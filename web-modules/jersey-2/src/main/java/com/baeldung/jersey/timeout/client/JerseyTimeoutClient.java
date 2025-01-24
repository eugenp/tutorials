package com.baeldung.jersey.timeout.client;

import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.baeldung.jersey.timeout.server.TimeoutResource;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation.Builder;

public class JerseyTimeoutClient {

    private static final long TIMEOUT = TimeoutResource.STALL / 2;

    private final String endpoint;

    public JerseyTimeoutClient(String endpoint) {
        this.endpoint = endpoint;
    }

    private String get(Client client) {
        return get(client, null);
    }

    private String get(Client client, Long requestTimeout) {
        Builder request = client.target(endpoint)
            .request();

        if (requestTimeout != null) {
            request.property(ClientProperties.CONNECT_TIMEOUT, requestTimeout);
            request.property(ClientProperties.READ_TIMEOUT, requestTimeout);
        }

        return request.get(String.class);
    }

    public String viaClientBuilder() {
        ClientBuilder builder = ClientBuilder.newBuilder()
            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);

        return get(builder.build());
    }

    public String viaClientConfig() {
        ClientConfig config = new ClientConfig();
        config.property(ClientProperties.CONNECT_TIMEOUT, TIMEOUT);
        config.property(ClientProperties.READ_TIMEOUT, TIMEOUT);

        return get(ClientBuilder.newClient(config));
    }

    public String viaClientProperty() {
        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, TIMEOUT);
        client.property(ClientProperties.READ_TIMEOUT, TIMEOUT);

        return get(client);
    }

    public String viaRequestProperty() {
        return get(ClientBuilder.newClient(), TIMEOUT);
    }
}
