package com.baeldung.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.core.java.properties.FileStreamsPropertyMutator;
import com.baeldung.core.java.properties.PropertyLoader;
import com.baeldung.core.java.properties.PropertyMutator;

public class FileStreamsPropertyMutatorUnitTest {

    private static final String PROPERTY_FILE_NAME = "app.properties";
    private final PropertyLoader propertyLoader = new PropertyLoader();
    private Properties initialProperties;

    private final PropertyMutator propertyMutator = new FileStreamsPropertyMutator(PROPERTY_FILE_NAME, propertyLoader);

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
    public void addProperty_whenUsingFileStreams_thenReturnsNewPropertyWithoutAffectingOtherProperties() throws IOException {
        Properties appProperties = propertyLoader.fromFile(PROPERTY_FILE_NAME);

        assertEquals("TestApp", appProperties.getProperty("name"));
        assertNull(appProperties.getProperty("new.property"));

        propertyMutator.addProperty("new.property", "new-value");

        appProperties = propertyLoader.fromFile(PROPERTY_FILE_NAME);
        assertEquals("new-value", appProperties.getProperty("new.property"));
        assertEquals("TestApp", appProperties.getProperty("name"));
    }

    @Test
    public void updateProperty_whenUsingFileStreams_thenReturnsUpdatedPropertyWithoutAffectingOtherProperties() throws IOException {
        Properties appProperties = propertyLoader.fromFile(PROPERTY_FILE_NAME);

        assertEquals("1.0", appProperties.getProperty("version"));
        assertEquals("TestApp", appProperties.getProperty("name"));

        propertyMutator.updateProperty("version", "2.0");

        appProperties = propertyLoader.fromFile(PROPERTY_FILE_NAME);
        assertEquals("2.0", appProperties.getProperty("version"));
        assertEquals("TestApp", appProperties.getProperty("name"));
    }
}
