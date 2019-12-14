package com.baeldung.openid.oidc.utils;

import java.io.IOException;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class YamlLoaderInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    private final String file;

    public YamlLoaderInitializer(String file) {
        this.file = file;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Resource path = new ClassPathResource(this.file);
        PropertySource<?> propertySource = loadYaml(path);
        applicationContext.getEnvironment()
            .getPropertySources()
            .addLast(propertySource);
    }

    private PropertySource<?> loadYaml(Resource path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.loader.load("custom-resource", path)
                .get(0);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load yaml configuration from" + path, ex);
        }
    }

}
