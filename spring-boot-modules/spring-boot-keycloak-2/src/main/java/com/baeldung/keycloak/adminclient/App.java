package com.baeldung.keycloak.adminclient;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = { "com.baeldung.keycloak.adminclient" })
@PropertySource("classpath:application-adminclient.properties")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
          .serverUrl("http://localhost:8080")
          .realm("master")
          .clientId("admin-cli")
          .grantType(OAuth2Constants.PASSWORD)
          .username("admin")
          .password("password")
          .build();
    }
}