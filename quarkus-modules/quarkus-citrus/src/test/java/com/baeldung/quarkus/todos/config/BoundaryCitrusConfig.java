package com.baeldung.quarkus.todos.config;

import static org.citrusframework.http.endpoint.builder.HttpEndpoints.http;

import org.citrusframework.http.client.HttpClient;
import org.citrusframework.spi.BindToRegistry;

public class BoundaryCitrusConfig {

    public static final String API_CLIENT = "apiClient";

    @BindToRegistry(name = API_CLIENT)
    public HttpClient apiClient() {
        return http().client()
            .requestUrl("http://localhost:8081")
            .build();
    }

}
