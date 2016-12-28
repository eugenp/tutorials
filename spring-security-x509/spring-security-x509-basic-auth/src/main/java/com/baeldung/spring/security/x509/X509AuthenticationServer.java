package com.baeldung.spring.security.x509;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableWebSecurity
public class X509AuthenticationServer extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(X509AuthenticationServer.class, args);
    }

}
