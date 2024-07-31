package com.baeldung.openid.oidc.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.openid.oidc.utils.YamlLoaderInitializer;

@SpringBootApplication
public class SpringOidcLoginApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringOidcLoginApplication.class);
        ApplicationContextInitializer<ConfigurableApplicationContext> yamlInitializer = new YamlLoaderInitializer("login-application.yml");
        application.addInitializers(yamlInitializer);
        application.run(args);
    }

}
