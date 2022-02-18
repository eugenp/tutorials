package com.baeldung.springcloudgateway.rewrite.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("url-rewrite")
public class DynamicRewriteRoute {
    
    @Value("${rewrite.backend.uri:http://example.com}")
    private String backendUri;
    

    @Bean
    public RouteLocator dynamicZipCodeRoute(RouteLocatorBuilder builder) {

        return builder.routes()
          .route("dynamicRewrite", r ->
             r.path("/api/v2/zip/**")
              .filters(f -> f.rewritePath("/api/v2/zip/(?<segment>.*)", "/api/v2/${segment}-NEW"))
              .uri(backendUri)
          )
          .build();
    }

}
