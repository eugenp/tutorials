package com.baeldung.springbootsecurity.oauth2resource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableResourceServer
@SpringBootApplication(scanBasePackages = "com.baeldung.springbootsecurity.oauth2resource")
public class SpringBootOAuth2ResourceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
          .profiles("resource")
          .sources(SpringBootOAuth2ResourceApplication.class)
          .build()
          .run(args);
    }

    @RestController
    class SecuredResourceController {

        @GetMapping("/securedResource")
        public String securedResource() {
            return "Baeldung Secured Resource OK";
        }

    }
}
