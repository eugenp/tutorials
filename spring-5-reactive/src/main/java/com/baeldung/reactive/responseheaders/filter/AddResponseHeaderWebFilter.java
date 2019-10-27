package com.baeldung.reactive.responseheaders.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class AddResponseHeaderWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getResponse()
            .getHeaders()
            .add("Baeldung-Example-Filter-Header", "Value-Filter");
        return chain.filter(exchange);
    }
}
