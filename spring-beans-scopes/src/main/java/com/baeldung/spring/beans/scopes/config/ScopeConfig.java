package com.baeldung.spring.beans.scopes.config;

import com.baeldung.spring.beans.scopes.domain.Coffee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Random;

/**
 * Created by shazi on 10/21/2017.
 */
@Configuration
public class ScopeConfig {

    private String[] coffeeTypes = new String[] {"Black", "White"};

    @Bean
    public Coffee brewedOncePerDay() {
        return new Coffee(coffeeTypes[0] + " per day", new Date());
    }

    @Bean
    @Scope("prototype")
    public Coffee freshlyBrewedOnLocalRequest() {
        return new Coffee(coffeeTypes[new Random().nextInt(1)] + " per local request", new Date());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Coffee onRequestBrewed() {
        return new Coffee(coffeeTypes[new Random().nextInt(1)] + " per online request", new Date());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Coffee perSessionBrewed() {
        return new Coffee(coffeeTypes[new Random().nextInt(1)] + " per online session", new Date());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_GLOBAL_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Coffee perGlobalSessionBrewed() {
        return new Coffee(coffeeTypes[new Random().nextInt(1)] + " per online global session", new Date());
    }

}
