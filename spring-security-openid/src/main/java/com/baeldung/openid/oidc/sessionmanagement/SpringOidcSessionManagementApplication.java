package com.baeldung.openid.oidc.sessionmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:sessionmanagement-application.properties")
public class SpringOidcSessionManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOidcSessionManagementApplication.class, args);
    }

}
