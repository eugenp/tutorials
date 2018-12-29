package com.baeldung.webclient.authcode.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * Note: This app is configured to use the authorization service and the resource service located in module spring-5-security-oauth
 * 
 * As we usually do with other well-known auth providers (github/facebook/...) we have to log-in using credentials handled by the auth server (bael-user/bael-password)
 * 
 * @author rozagerardo
 *
 */
@PropertySource("classpath:webclient-login-application.properties")
@SpringBootApplication
public class OauthLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthLoginApplication.class, args);
    }

}
