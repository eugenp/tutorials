package com.baeldung.keycloaksoap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeycloakSoapServicesApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KeycloakSoapServicesApplication.class);
        application.setAdditionalProfiles("keycloak");
        application.run(args);
    }

}
