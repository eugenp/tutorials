package com.baeldung.valuewithdefaults;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;

/**
 * Demonstrates setting defaults for @Value annotation.  Note that there are no properties 
 * defined in the specified property source.  We also assume that the user here
 * does not have a system property named some.system.key.
 *
 */
@Configuration
@PropertySource(name = "myProperties", value = "valueswithdefaults.properties")
public class ValuesWithDefaultsApp {

    @Value("${some.key:my default value}")
    private String withDefaultValue;

    @Value("#{systemProperties['some.system.key'] ?: 'my default system property value'}")
    private String spelWithDefaultValue;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ValuesWithDefaultsApp.class);
    }

    @PostConstruct
    public void afterInitialize() {
    	Assert.isTrue(withDefaultValue.equals("my default value"));
    	Assert.isTrue(spelWithDefaultValue.equals("my default system property value"));
    }
}
