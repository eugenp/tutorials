package com.baeldung.quarkus.todos.config;

import static org.citrusframework.http.endpoint.builder.HttpEndpoints.http;

import org.citrusframework.http.client.HttpClient;
import org.citrusframework.spi.BindToRegistry;

public class BoundaryOpenApiCitrusConfig {

    public static final String API_CLIENT = "openApiClient";

    @BindToRegistry(name = API_CLIENT)
    public HttpClient openApiClient() {
        return http().client()
            .requestUrl("http://localhost:8081/api/v1")
            .build();
    }

}