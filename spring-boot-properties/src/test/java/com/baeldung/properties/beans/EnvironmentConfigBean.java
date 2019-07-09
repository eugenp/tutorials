package com.baeldung.properties.beans;

import com.baeldung.properties.configs.ReloadablePropertySourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "file:extra2.properties", factory = ReloadablePropertySourceFactory.class)
public class EnvironmentConfigBean {

    private Environment environment;

    public EnvironmentConfigBean(@Autowired Environment environment) {
        this.environment = environment;
    }

    public String getProperty1() {
        return environment.getProperty("application.dynamic.prop1");
    }

    public String getProperty2() {
        return environment.getProperty("application.dynamic.prop2");
    }
}
