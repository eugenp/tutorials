package com.baeldung.spring.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfig.class)
public class SpringWebfluxAmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxAmqpApplication.class, args);
    }
    
    
    /**
     * This is a workaround for https://github.com/spring-projects/spring-framework/issues/21094
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }
    
}
