package com.baeldung.graphqlvsrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
  SecurityAutoConfiguration.class,
  HibernateJpaAutoConfiguration.class
})
public class GraphqlVsRestApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "rest-vs-graphql");
        SpringApplication.run(GraphqlVsRestApplication.class, args);
    }

}