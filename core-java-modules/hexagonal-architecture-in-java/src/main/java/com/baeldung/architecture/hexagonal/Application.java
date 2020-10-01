package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.UserResources;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    public static class JerseyConfiguration extends ResourceConfig {

        public JerseyConfiguration() {
            register(UserResources.class);
        }
    }
}
