package com.baeldung.loginredirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring-security-login-redirect.xml"})
class LoginRedirectApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginRedirectApplication.class, args);
    }
}