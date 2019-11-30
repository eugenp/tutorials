package com.baeldung.openid.oidc.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:login-application.properties")
public class SpringOidcLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOidcLoginApplication.class, args);
    }

}
