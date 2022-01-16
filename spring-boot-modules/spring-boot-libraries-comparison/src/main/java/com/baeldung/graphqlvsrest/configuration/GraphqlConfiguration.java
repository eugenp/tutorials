package com.baeldung.graphqlvsrest.configuration;

import com.baeldung.graphqlvsrest.resolver.Mutation;
import com.baeldung.graphqlvsrest.resolver.Query;
import com.baeldung.graphqlvsrest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphqlConfiguration {

    @Autowired
    ProductRepository productRepository;

    @Bean
    public Query query() {
        return new Query(productRepository);
    }


    @Bean
    public Mutation mutation() {
        return new Mutation(productRepository);
    }
}
