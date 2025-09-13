package com.baeldung.core.java.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApacheCommonsPropertyMutatorUnitTest {

    private static final String PROPERTY_FILE_NAME = "app.properties";
    private final PropertyLoader propertyLoader = new PropertyLoader();
    private Properties initialProperties;

    private final ApacheCommonsPropertyMutator propertyMutator = new ApacheCommonsPropertyMutator(PROPERTY_FILE_NAME);

    @BeforeEach
    public void loadInitialPropertiesFromFile() throws IOException {
        initialProperties = propertyLoader.fromFile(PROPERTY_FILE_NAME);
    }

    @AfterEach
    public void restoreInitialPropertiesToFile() throws IOException {
        FileOutputStream out = new FileOutputStream(propertyLoader.getFilePathFromResources(PROPERTY_FILE_NAME));
        initialProperties.store(out, null);
        out.close();
    }

    @Test
    public void givenApacheCommons_whenAddNonExistingProperty_thenNewPropertyWithoutAffectingOtherProperties() throws ConfigurationException {
        assertNull(propertyMutator.getProperty("new.property"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));

        propertyMutator.addOrUpdateProperty("new.property", "new-value");

        assertEquals("new-value", propertyMutator.getProperty("new.property"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));
    }

    @Test
    public void givenApacheCommons_whenUpdateExistingProperty_thenUpdatedPropertyWithoutAffectingOtherProperties() throws ConfigurationException {
        assertEquals("1.0", propertyMutator.getProperty("version"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));

        propertyMutator.addOrUpdateProperty("version", "2.0");

        assertEquals("2.0", propertyMutator.getProperty("version"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));
    }
}
