package com.baeldung.filters;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public final class WebClientFilters {

    public static ExchangeFilterFunction modifyRequestHeaders(final MultiValueMap<String, String> changedMap) {
        return (request, next) -> {
            final ClientRequest clientRequest = ClientRequest.from(request)
                .header("traceId", "TRACE-ID")
                .build();
            changedMap.addAll(clientRequest.headers());
            return next.exchange(clientRequest);
        };
    }
}
