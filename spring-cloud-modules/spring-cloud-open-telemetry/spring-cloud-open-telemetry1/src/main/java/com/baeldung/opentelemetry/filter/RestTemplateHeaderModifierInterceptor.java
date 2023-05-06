package com.baeldung.opentelemetry.filter;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author : Nam Thang
 * @since : 04/05/2023, Thu
 **/
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHeaderModifierInterceptor.class);

    private final OpenTelemetry openTelemetry = GlobalOpenTelemetry.get();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        openTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), request, (carrier, key, value) -> {
            // Insert the context as Header
            carrier.getHeaders().set(key, value);
            LOGGER.info("Injecting trace context into header: {}={}", key, value);
        });
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }
}
