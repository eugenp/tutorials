package com.baeldung;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.baeldung.rest.RestConfig;
import com.baeldung.services.ServiceConfig;
import com.baeldung.web.WebConfig;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        new SpringApplicationBuilder().parent(ServiceConfig.class)
            .web(WebApplicationType.NONE)
            .child(WebConfig.class)
            .web(WebApplicationType.SERVLET)
            .sibling(RestConfig.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }
}
