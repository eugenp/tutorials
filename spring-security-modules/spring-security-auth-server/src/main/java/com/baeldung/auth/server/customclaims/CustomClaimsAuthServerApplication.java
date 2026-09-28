package com.baeldung.auth.server.customclaims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(com.baeldung.auth.server.customclaims.config.AuthServerConfiguration.class)
public class CustomClaimsAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomClaimsAuthServerApplication.class, args);
    }
}
