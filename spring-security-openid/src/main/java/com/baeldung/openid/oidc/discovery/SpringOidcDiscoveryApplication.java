package com.baeldung.openid.oidc.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:discovery-application.properties")
public class SpringOidcDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOidcDiscoveryApplication.class, args);
    }

}
