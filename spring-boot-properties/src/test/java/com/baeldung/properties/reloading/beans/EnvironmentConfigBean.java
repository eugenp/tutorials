package com.baeldung.properties.reloading.beans;

import com.baeldung.properties.reloading.configs.ReloadablePropertySourceFactory;
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

    public String getColor() {
        return environment.getProperty("application.theme.color");
    }

    public String getBackgroundColor() {
        return environment.getProperty("application.theme.background");
    }
}
