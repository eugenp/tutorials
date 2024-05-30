package com.baeldung.quarkus.todos.config;

import org.citrusframework.http.client.HttpClient;
import org.citrusframework.spi.BindToRegistry;

import static org.citrusframework.http.endpoint.builder.HttpEndpoints.http;

public class BoundaryOpenApiCitrusConfig {

    public static final String API_CLIENT = "openApiClient";

    @BindToRegistry(name = API_CLIENT)
    public HttpClient openApiClient() {
        return http()
                .client()
                .requestUrl("http://localhost:8081/api/v1")
                .build();
    }

}