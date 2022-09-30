package com.baeldung.intro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by smatt on 21/08/2017.
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ShiroSpringApplication {

    private static final transient Logger log = LoggerFactory.getLogger(ShiroSpringApplication.class);

    public static void main(String... args) {
        SpringApplication.run(ShiroSpringApplication.class, args);
    }


    @Bean
    public Realm realm() {
        return new MyCustomRealm();
    }


    @Bean
    public ShiroFilterChainDefinition filterChainDefinition() {
        DefaultShiroFilterChainDefinition filter
          = new DefaultShiroFilterChainDefinition();

        filter.addPathDefinition("/secure", "authc");
        filter.addPathDefinition("/**", "anon");

        return filter;
    }




}
