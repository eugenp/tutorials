package com.baeldung.reactive.kdoyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class RealTimeTestApplication {

    @Bean
    public WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RealTimeTestApplication.class, args);
        context.getBean(RealTimeTest.class).read();
    }
}
