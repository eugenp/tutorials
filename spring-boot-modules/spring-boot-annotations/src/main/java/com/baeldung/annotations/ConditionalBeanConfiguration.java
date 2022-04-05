package com.baeldung.annotations;

import java.util.Properties;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalBeanConfiguration {

    @Conditional(HibernateCondition.class)
    Properties additionalProperties() {
        // application specific properties
        return new Properties();
    }
}
