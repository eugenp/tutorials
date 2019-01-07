package com.baeldung.webclient.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@PropertySource("webclient-resources-application.properties")
@SpringBootApplication
public class ResourceServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

}
