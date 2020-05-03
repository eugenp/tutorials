package com.baeldung.springbootsecurity.oauth2server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication(scanBasePackages = "com.baeldung.springbootsecurity.oauth2server")
public class SpringBootAuthorizationServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootAuthorizationServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAuthorizationServerApplication.class, args);
    }

    @RestController
    class UserController {

        @GetMapping("/user")
        public Principal user(Principal user) {
            return user;
        }

        @GetMapping("/authentication")
        public Object getAuthentication(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
            logger.info("authentication -> {}", authentication);
            return authentication.getDetails();
        }

        @GetMapping("/principal")
        public String getPrincipal(@CurrentSecurityContext(expression = "authentication.principal") Principal principal) {
            logger.info("principal -> {}", principal);
            return principal.getName();
        }
    }
}
