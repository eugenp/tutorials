package com.baeldung.loginredirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
@ImportResource({"classpath*:spring-security-login-redirect.xml"})
class LoginRedirectApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginRedirectApplication.class, args);
    }
}