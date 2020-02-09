package com.baeldung.openid.oidc.sessionmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.openid.oidc.utils.YamlLoaderInitializer;

@SpringBootApplication
public class SpringOidcSessionManagementApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringOidcSessionManagementApplication.class);
        ApplicationContextInitializer<ConfigurableApplicationContext> yamlInitializer = new YamlLoaderInitializer("sessionmanagement-application.yml");
        application.addInitializers(yamlInitializer);
        application.run(args);
    }

}
