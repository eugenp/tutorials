package com.baeldung.ssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-defaults.properties")
public class HttpsEnabledApplication {

    public static void main(String... args) {
        SpringApplication application = new SpringApplication(HttpsEnabledApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
    }
}
