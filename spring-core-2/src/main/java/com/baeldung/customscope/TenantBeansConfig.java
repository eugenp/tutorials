package com.baeldung.customscope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TenantBeansConfig {

    @Scope(scopeName = "tenant")
    @Bean
    public TenantBean foo() {
        return new TenantBean("foo");
    }

    @Scope(scopeName = "tenant")
    @Bean
    public TenantBean bar() {
        return new TenantBean("bar");
    }
}
