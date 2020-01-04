package com.baeldung.springcloudgateway.customfilters.gatewayapp.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories.LoggingGatewayFilterFactory;
import com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories.LoggingGatewayFilterFactory.Config;

/**
 * Note: We want to keep this as an example of configuring a Route with a custom filter
 *
 * This corresponds with the properties configuration we have
 */
// @Configuration
public class ServiceRouteConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, LoggingGatewayFilterFactory loggingFactory) {

        return builder.routes()
            .route("service_route_java_config", r -> r.path("/service/**")
                .filters(f -> f.rewritePath("/service(?<segment>/?.*)", "$\\{segment}")
                    .filter(loggingFactory.apply(new Config("My Custom Message", true, true))))
                .uri("http://localhost:8081"))
            .build();
    }
}
