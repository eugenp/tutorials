package com.baeldung.hexagonalarchitecture2.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonalarchitecture2.core.RockPaperScissorsService;

@Configuration
public class GraphQlConfiguration {
    @Bean
    public Query query() {
        return new Query();
    }

    @Bean
    public Mutation mutation(RockPaperScissorsService service) {
        return new Mutation(service);
    }
}
