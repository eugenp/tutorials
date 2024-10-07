package com.baeldung.tls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class TLSEnabledApplication {

    public static void main(String... args) {
        SpringApplication application = new SpringApplication(TLSEnabledApplication.class);
        application.setAdditionalProfiles("tls");
        application.run(args);
    }
}
