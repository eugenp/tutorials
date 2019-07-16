package org.baeldung.ssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpsEnabledApplication {

    public static void main(String... args) {
        SpringApplication application = new SpringApplication(HttpsEnabledApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
    }
}
