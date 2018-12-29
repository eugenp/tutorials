package com.baeldung.webclient.manualrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * Note: This app is configured to use the authorization service and the resource service located in module spring-5-security-oauth
 * 
 * As we usually do with other well-known auth providers (github/facebook/...) we have to log-in using user credentials (bael-user/bael-password) and client configurations (bael-client-id/bael-secret) handled by the auth server
 * 
 * @author rozagerardo
 *
 */
@SpringBootApplication
public class ManualRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManualRequestApplication.class, args);
    }

}
