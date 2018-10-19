package org.baeldung.demo;

import com.baeldung.graphql.GraphqlConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GraphqlConfiguration.class)
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "demo");
        SpringApplication.run(DemoApplication.class, args);
    }

}
