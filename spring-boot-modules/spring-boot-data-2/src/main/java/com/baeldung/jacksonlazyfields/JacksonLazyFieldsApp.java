package com.baeldung.jacksonlazyfields;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@SpringBootApplication
@PropertySource("classpath:com/baeldung/jacksonlazyfields/application.properties")
public class JacksonLazyFieldsApp {

    public static void main(String[] args) {
        SpringApplication.run(JacksonLazyFieldsApp.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate6Module());
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }
}
