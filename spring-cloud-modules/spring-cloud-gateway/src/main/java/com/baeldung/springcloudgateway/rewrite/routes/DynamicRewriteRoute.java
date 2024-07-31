package com.baeldung.springcloudgateway.rewrite.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.server.reactive.ServerHttpRequest;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

import java.util.Random;

@Configuration
@Profile("url-rewrite")
public class DynamicRewriteRoute {
    
    @Value("${rewrite.backend.uri}")
    private String backendUri;
    private static Random rnd = new Random();
    
    @Bean
    public RouteLocator dynamicZipCodeRoute(RouteLocatorBuilder builder) {
        return builder.routes()
          .route("dynamicRewrite", r ->
             r.path("/v2/zip/**")
              .filters(f -> f.filter((exchange, chain) -> {
                  ServerHttpRequest req = exchange.getRequest();
                  addOriginalRequestUrl(exchange, req.getURI());
                  String path = req.getURI().getRawPath();
                  String newPath = path.replaceAll(
                    "/v2/zip/(?<zipcode>.*)", 
                    "/api/zip/${zipcode}-" + String.format("%03d", rnd.nextInt(1000)));
                  ServerHttpRequest request = req.mutate().path(newPath).build();
                  exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
                  return chain.filter(exchange.mutate().request(request).build());
              }))
              .uri(backendUri))
          .build();
    }
}
