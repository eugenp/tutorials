package com.baeldung.springcloudgateway.webfilters.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import reactor.core.publisher.Mono;

@Configuration
public class ModifyBodyRouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("modify_request_body", r -> r.path("/post")
                .filters(f -> f.modifyRequestBody(String.class, Hello.class, MediaType.APPLICATION_JSON_VALUE,
                        (exchange, s) -> Mono.just(new Hello(s.toUpperCase())))).uri("https://httpbin.org"))
            .build();
    }  

    @Bean
    public RouteLocator responseRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("modify_response_body", r -> r.path("/put/**")
                .filters(f -> f.modifyResponseBody(String.class, Hello.class, MediaType.APPLICATION_JSON_VALUE,
                        (exchange, s) -> Mono.just(new Hello("New Body")))).uri("https://httpbin.org"))
            .build();
    } 

    static class Hello {
        String message;

        public Hello() { }

        public Hello(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    } 
}
