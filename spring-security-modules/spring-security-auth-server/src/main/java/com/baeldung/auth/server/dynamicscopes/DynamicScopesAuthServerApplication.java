package com.baeldung.auth.server.dynamicscopes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(com.baeldung.auth.server.dynamicscopes.config.AuthServerConfiguration.class)
public class DynamicScopesAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicScopesAuthServerApplication.class, args);
    }
}
