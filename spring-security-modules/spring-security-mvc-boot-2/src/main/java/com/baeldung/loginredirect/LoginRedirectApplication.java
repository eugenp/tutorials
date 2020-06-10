package com.baeldung.loginredirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ImportResource({"classpath*:spring-security-login-redirect.xml"})
class LoginRedirectApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginRedirectApplication.class, args);
    }
}