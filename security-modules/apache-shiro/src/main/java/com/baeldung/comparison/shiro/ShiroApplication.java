package com.baeldung.comparison.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ShiroApplication {

    public static void main(String... args) {
        SpringApplication.run(ShiroApplication.class, args);
    }

    @Bean
    public Realm customRealm() {
        return new CustomRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition filter = new DefaultShiroFilterChainDefinition();

        filter.addPathDefinition("/home", "authc");
        filter.addPathDefinition("/**", "anon");

        return filter;
    }

}
