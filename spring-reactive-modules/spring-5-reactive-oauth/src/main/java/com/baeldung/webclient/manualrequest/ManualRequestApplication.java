package com.baeldung.webclient.manualrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * Note: This app is configured to use the authorization service and the resource service located in Baeldung/spring-security-oauth repo
 * 
 * @author rozagerardo
 *
 */
@PropertySource("classpath:webclient-manual-request-oauth-application.properties")
@SpringBootApplication
public class ManualRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManualRequestApplication.class, args);
    }

}
