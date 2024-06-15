package com.baeldung.commons.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.combined.MultiFileConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.junit.jupiter.api.Test;

class MultiFileConfigurationBuilderUnitTest {

    @Test
    void givenMultiplePropertyFiles_whenReadingWithMultiFileConfigurationBuilder_thenIsLoaded() throws ConfigurationException {
        System.setProperty("tenant", "A");
        String filePattern = "src/test/resources/configuration/tenant-${sys:tenant}.properties";
        MultiFileConfigurationBuilder<PropertiesConfiguration> builder = new MultiFileConfigurationBuilder<>(PropertiesConfiguration.class).configure(
            new Parameters().multiFile()
                .setFilePattern(filePattern)
                .setPrefixLookups(ConfigurationInterpolator.getDefaultPrefixLookups()));
        Configuration config = builder.getConfiguration();
        String tenantAName = config.getString("name");

        assertEquals("Tenant A", tenantAName);
    }
}
