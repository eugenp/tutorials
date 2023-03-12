package com.baeldung.dynamicendpoints.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public class ReloadablePropertySource extends PropertySource {

    private final PropertiesConfiguration propertiesConfiguration;

    public ReloadablePropertySource(String name, PropertiesConfiguration propertiesConfiguration) {
        super(name);
        this.propertiesConfiguration = propertiesConfiguration;
    }

    public ReloadablePropertySource(String name, String path) throws ConfigurationException {
        super(StringUtils.hasText(name) ? path : name);
        try {
            this.propertiesConfiguration = new PropertiesConfiguration(path);
            this.propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (PropertiesException e) {
            throw e;
        }
    }

    @Override
    public Object getProperty(String s) {
        return propertiesConfiguration.getProperty(s);
    }
}