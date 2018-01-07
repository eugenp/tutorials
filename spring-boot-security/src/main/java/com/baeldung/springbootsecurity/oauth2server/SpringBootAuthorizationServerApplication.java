package com.baeldung.springbootsecurity.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@EnableAuthorizationServer
@SpringBootApplication(scanBasePackages = "com.baeldung.springbootsecurity.oauth2server")
public class SpringBootAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAuthorizationServerApplication.class, args);
    }

    @RestController
    @RequestMapping("/user")
    class UserController {

        @GetMapping("/me")
        public Principal me(Principal principal) {
            return principal;
        }
    }
}
