package com.baeldung.springcloudgateway.customfilters.filters.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

@Configuration
public class LoggingGlobalFiltersConfigurations {

    final Logger logger = LoggerFactory.getLogger(LoggingGlobalFiltersConfigurations.class);

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logger.info("Global Post Filter executed");
                }));
        };
    }

    @Bean
    @Order(-1)
    public GlobalFilter FirstPreLastPostGlobalFilter() {
        return (exchange, chain) -> {
            logger.info("First Pre Global Filter");
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logger.info("Last Post Global Filter");
                }));
        };
    }

}
