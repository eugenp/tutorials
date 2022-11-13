package com.baeldung.oauth2resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-oauth2-rest-template.properties")
public class SpringSecurityOauth2ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOauth2ClientApplication.class, args);
    }

}
