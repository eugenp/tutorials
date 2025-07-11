package com.baeldung.core.java.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileStreamsPropertyMutatorUnitTest {

    private static final String PROPERTY_FILE_NAME = "app.properties";
    private final PropertyLoader propertyLoader = new PropertyLoader();
    private Properties initialProperties;

    private final FileStreamsPropertyMutator propertyMutator = new FileStreamsPropertyMutator(PROPERTY_FILE_NAME, propertyLoader);

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
    public void givenFileStreams_whenAddNonExistingProperty_thenNewPropertyWithoutAffectingOtherProperties() throws IOException {
        assertEquals("TestApp", propertyMutator.getProperty("name"));
        assertNull(propertyMutator.getProperty("new.property"));

        propertyMutator.addOrUpdateProperty("new.property", "new-value");

        assertEquals("new-value", propertyMutator.getProperty("new.property"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));
    }

    @Test
    public void givenFileStreams_whenUpdateExistingProperty_thenUpdatedPropertyWithoutAffectingOtherProperties() throws IOException {
        assertEquals("1.0", propertyMutator.getProperty("version"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));

        propertyMutator.addOrUpdateProperty("version", "2.0");

        assertEquals("2.0", propertyMutator.getProperty("version"));
        assertEquals("TestApp", propertyMutator.getProperty("name"));
    }
}
