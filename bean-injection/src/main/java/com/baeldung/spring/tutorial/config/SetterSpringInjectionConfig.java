package com.baeldung.spring.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.tutorial.InjectedObject;
import com.baeldung.spring.tutorial.SetterInjectionReceiver;

/**
 * Config class for the Setter-based injection config.
 * @author DamCx
 */
@Configuration
public class SetterSpringInjectionConfig {
    /**
     * Injected Object.
     * @return the Injected Object
     */
    @Bean
    public InjectedObject getInjectedObject() {
        return new InjectedObject();
    }

    /**
     * Setter-based injection config.
     * @return The SetterInjectionReceiver
     */
    @Bean
    public SetterInjectionReceiver getSetterInjectionReceiver() {
        final SetterInjectionReceiver setterInjectionReceiver = new SetterInjectionReceiver();
        setterInjectionReceiver.setInternalSetterInjectedObject(this.getInjectedObject());
        return setterInjectionReceiver;
    }
}
