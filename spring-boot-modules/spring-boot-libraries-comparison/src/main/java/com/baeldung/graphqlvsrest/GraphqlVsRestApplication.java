package com.baeldung.graphqlvsrest;

import com.baeldung.graphqlvsrest.configuration.GraphqlConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GraphqlConfiguration.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class GraphqlVsRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlVsRestApplication.class, args);
    }

}