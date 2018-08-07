package com.baeldung.event.streaming.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.event.streaming.client.webclient.EventStreamingWebClient;

@SpringBootApplication
public class EventStreamingApplicationClient {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EventStreamingApplicationClient.class);
        Map<String, Object> properties = new HashMap<>();
        properties.put("server.port", "8090");
        properties.put("api.base.url.transactions", "http://localhost:8080");
        properties.put("api.endpoint.all.transactions", "/transactions");
        properties.put("api.username", "user");
        properties.put("api.password", "password");
        springApplication.setDefaultProperties(properties);
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        EventStreamingWebClient client = applicationContext.getBean(EventStreamingWebClient.class);
        client.listen();
    }
}
