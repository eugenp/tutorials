package com.baeldung.springbootsecurity.basic_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
  SecurityAutoConfiguration.class
  //    ,ManagementWebSecurityAutoConfiguration.class
}, scanBasePackages = "com.baeldung.springbootsecurity.basic_auth")
public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }
}
