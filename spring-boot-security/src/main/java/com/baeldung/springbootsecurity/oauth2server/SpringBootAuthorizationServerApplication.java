package com.baeldung.springbootsecurity.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@SpringBootApplication(scanBasePackages = "com.baeldung.springbootsecurity.oauth2server")
public class SpringBootAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAuthorizationServerApplication.class, args);
    }

}
