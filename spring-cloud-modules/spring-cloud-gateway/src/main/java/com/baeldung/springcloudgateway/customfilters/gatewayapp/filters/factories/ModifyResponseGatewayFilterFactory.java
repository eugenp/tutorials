package com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ModifyResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<ModifyResponseGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(ModifyResponseGatewayFilterFactory.class);

    public ModifyResponseGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    ServerHttpResponse response = exchange.getResponse();

                    Optional.ofNullable(exchange.getRequest()
                        .getQueryParams()
                        .getFirst("locale"))
                        .ifPresent(qp -> {
                            String responseContentLanguage = response.getHeaders()
                                .getContentLanguage()
                                .getLanguage();

                            response.getHeaders()
                                .add("Bael-Custom-Language-Header", responseContentLanguage);
                            logger.info("Added custom header to Response");
                        });
                }));
        };
    }

    public static class Config {
    }
}
