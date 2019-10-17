package com.baeldung.springbootsecurity.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication(scanBasePackages = "com.baeldung.springbootsecurity.oauth2server")
public class SpringBootAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAuthorizationServerApplication.class, args);
    }

    @RestController
    class UserController {

        @GetMapping("/user")
        public Principal user(Principal user) {
            return user;
        }

    }
}
