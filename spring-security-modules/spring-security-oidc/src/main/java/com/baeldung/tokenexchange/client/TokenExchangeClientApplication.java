package com.baeldung.tokenexchange.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.tokenexchange.authserver.AuthServerApplication;

@SpringBootApplication
public class TokenExchangeClientApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TokenExchangeClientApplication.class);
        application.setAdditionalProfiles("client");
        application.run(args);
    }

}
