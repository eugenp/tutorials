package com.baeldung.errorhandling;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@SpringBootApplication
public class Main {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           @Value("${httpbin}") String httpbin,
                                           MyCustomFilter myCustomFilter) {
        return builder.routes()
                .route("error_404", r -> r.path("/test/error_404")
                        .filters(f -> f.filter(myCustomFilter).filter((exchange, chain) -> switchRequestUri(exchange, chain, "error_404", "404")))
                        .uri(httpbin + "/status/404"))
                .route("error_500", r -> r.path("/test/error_500")
                        .filters(f -> f.filter(myCustomFilter).filter((exchange, chain) -> switchRequestUri(exchange, chain, "error_500", "500")))
                        .uri(httpbin+"/status/500"))
                .route("error_400", r -> r.path("/test/error_400")
                        .filters(f -> f.filter(myCustomFilter).filter((exchange, chain) -> switchRequestUri(exchange, chain, "error_400", "400")))
                        .uri(httpbin+"/status/400"))
                .route("error_409", r -> r.path("/test/error_409")
                        .filters(f -> f.filter(myCustomFilter).filter((exchange, chain) -> switchRequestUri(exchange, chain, "error_409", "409")))
                        .uri(httpbin+"/status/409"))
                .route("custom_rate_limit", r -> r.path("/test/custom_rate_limit").filters(f -> f.filter(myCustomFilter)).uri(httpbin+"/uuid"))
                .route("custom_auth", r -> r.path("/test/custom_auth").filters(f -> f.filter(myCustomFilter)).uri(httpbin+"/api/custom_auth"))
                .route("anything", r -> r.path("/test/anything")
                        .filters(f -> f.changeRequestUri((exchange) -> Optional.of(UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
                                        .host("httpbin.org")
                                        .port(80)
                                        .replacePath("/anything").build().toUri())))
                        .uri("http://httpbin.org"))
                .build();


    }

    private Mono<Void> switchRequestUri(ServerWebExchange exchange,
                                        GatewayFilterChain chain,
                                        String externalUri,
                                        String internalUri) {
        ServerHttpRequest req = exchange.getRequest();
        addOriginalRequestUrl(exchange, req.getURI());
        String path = req.getURI().getRawPath();
        String newPath = path.replaceAll("/test/" + externalUri, "/status/" + internalUri);
        ServerHttpRequest request = req.mutate().path(newPath).build();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
        return chain.filter(exchange.mutate().request(request).build());
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}