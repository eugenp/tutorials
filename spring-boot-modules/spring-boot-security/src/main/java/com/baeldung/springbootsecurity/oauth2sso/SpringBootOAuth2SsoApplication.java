package com.baeldung.springbootsecurity.oauth2sso;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableOAuth2Sso
@SpringBootApplication(scanBasePackages = "com.baeldung.springbootsecurity.oauth2sso")
public class SpringBootOAuth2SsoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
          .profiles("sso")
          .sources(SpringBootOAuth2SsoApplication.class)
          .build()
          .run(args);
    }
}
