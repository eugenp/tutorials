package io.orkes.demo.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.conductor.common.config.ObjectMapperProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
@ComponentScan(basePackages = { "io.orkes" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // ObjectMapper instance used for JSON serialization - can be modified to configure additional modules
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapperProvider().getObjectMapper();
    }

}
