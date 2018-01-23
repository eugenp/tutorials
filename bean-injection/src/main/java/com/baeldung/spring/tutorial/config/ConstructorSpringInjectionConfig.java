package com.baeldung.spring.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.tutorial.ConstructorInjectionReceiver;
import com.baeldung.spring.tutorial.InjectedObject;

/**
 * Config class for the Constructor-based injection config.
 * @author DamCx
 */
@Configuration
public class ConstructorSpringInjectionConfig {
    /**
     * Injected Object.
     * @return the Injected Object
     */
    @Bean
    public InjectedObject getInjectedObject() {
        return new InjectedObject();
    }

    /**
     * Constructor-based injection config.
     * @return The ConstructorInjectionReceiver
     */
    @Bean
    public ConstructorInjectionReceiver getConstructorInjectionReceiver() {
        return new ConstructorInjectionReceiver(this.getInjectedObject());

    }
}
