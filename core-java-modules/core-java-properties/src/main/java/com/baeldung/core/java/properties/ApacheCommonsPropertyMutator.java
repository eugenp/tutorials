package com.baeldung.core.java.properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ApacheCommonsPropertyMutator implements PropertyMutator {

    private final String propertyFileName;

    public ApacheCommonsPropertyMutator(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    public String getProperty(String key) throws ConfigurationException {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = getAppPropertiesConfigBuilder();
        Configuration properties = builder.getConfiguration();

        return (String) properties.getProperty(key);
    }

    @Override
    public void addOrUpdateProperty(String key, String value) throws ConfigurationException {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = getAppPropertiesConfigBuilder();
        Configuration configuration = builder.getConfiguration();

        configuration.setProperty(key, value);
        builder.save();
    }

    private FileBasedConfigurationBuilder<FileBasedConfiguration> getAppPropertiesConfigBuilder() {
        Parameters params = new Parameters();

        return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class).configure(params.properties()
          .setFileName(propertyFileName));
    }
}
