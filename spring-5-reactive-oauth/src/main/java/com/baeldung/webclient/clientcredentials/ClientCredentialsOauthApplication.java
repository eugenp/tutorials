package com.baeldung.webclient.clientcredentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import reactor.core.publisher.Hooks;

/**
 * 
 * Note: This app is configured to use the authorization service and the resource service located in module spring-5-security-oauth
 * 
 * As we usually do with other well-known auth providers (github/facebook/...) we have to log-in using credentials handled by the auth server (bael-user/bael-password)
 * 
 * @author rozagerardo
 *
 */
@PropertySource("classpath:webclient-client-credentials-oauth-application.properties")
@EnableScheduling
@SpringBootApplication
public class ClientCredentialsOauthApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(ClientCredentialsOauthApplication.class, args);
    }

}
