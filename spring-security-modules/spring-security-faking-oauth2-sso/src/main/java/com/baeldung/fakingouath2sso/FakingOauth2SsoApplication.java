package com.baeldung.fakingouath2sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FakingOauth2SsoApplication {

    @GetMapping("/")
    public String get() {
        return "Login Success!";
    }

    public static void main(String[] args) {
        SpringApplication.run(FakingOauth2SsoApplication.class, args);
    }

}
