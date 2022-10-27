package com.baeldung.web.https;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "com.baeldung.web.https")
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class HttpsEnabledApplication {
    public static void main(String... args) {
        SpringApplication application = new SpringApplication(HttpsEnabledApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
    }
}
