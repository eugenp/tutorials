package com.baeldung.beaninjectiontypes.javaconfigexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInjectorConfig {

    @Bean
    public SimpleDependency simpleDependency() {
        return new SimpleDependency();
    }

    // The following illustrates constructor injection:

    @Bean
    public SimpleBeanConstructorInjection simpleBeanConstructorInjection() {
        return new SimpleBeanConstructorInjection(simpleDependency());
    }

    // The following illustrates setter injection:

    @Bean
    public SimpleBeanSetterInjection simpleBeanSetterInjection() {
        SimpleBeanSetterInjection simpleBeanSetterInjection = new SimpleBeanSetterInjection();
        simpleBeanSetterInjection.setSimpleDependency(new SimpleDependency());
        return simpleBeanSetterInjection;
    }
}
