package com.baeldung.springcloudgateway.ipaddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication()
@PropertySource("classpath:ipaddress-application.properties")
public class IpAddressApplication {
    public static void main(String[] args) {
        SpringApplication.run(IpAddressApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("requestratelimiter_route", p -> p
                        .path("/example")
                        .filters(f -> f.requestRateLimiter(r -> r.setRateLimiter(redisRateLimiter())))
                        .uri("http://example.org"))
                .route("ipaddress_route", p -> p
                        .path("/example2")
                        .filters(f -> f.requestRateLimiter(r -> r.setRateLimiter(redisRateLimiter())
                                .setDenyEmptyKey(false)
                                .setKeyResolver(new SimpleClientAddressResolver())))
                        .uri("http://example.org"))
                .build();
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1, 1);
    }
}
