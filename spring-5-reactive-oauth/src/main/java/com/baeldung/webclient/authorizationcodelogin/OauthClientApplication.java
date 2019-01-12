package com.baeldung.webclient.authorizationcodelogin;

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
@PropertySource("classpath:webclient-auth-code-login-application.properties")
@SpringBootApplication
public class OauthClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthClientApplication.class, args);
    }

}
