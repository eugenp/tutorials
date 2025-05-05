package com.baeldung.core.java.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileAPIPropertyMutatorUnitTest {

    private static final String PROPERTY_FILE_NAME = "app.properties";
    private final PropertyLoader propertyLoader = new PropertyLoader();
    private Properties initialProperties;

    private final FileAPIPropertyMutator propertyMutator = new FileAPIPropertyMutator(PROPERTY_FILE_NAME, propertyLoader);

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
    public void givenFilesAPI_whenAddNonExistingProperty_thenNewPropertyWithoutAffectingOtherProperties() throws IOException {
        assertEquals("name=TestApp", propertyMutator.getPropertyKeyWithValue(1));

        propertyMutator.addPropertyKeyWithValue("new.property=new-value");

        assertEquals("new.property=new-value", propertyMutator.getLastPropertyKeyWithValue());
        assertEquals("name=TestApp", propertyMutator.getPropertyKeyWithValue(1));
    }

    @Test
    public void givenFilesAPI_whenUpdateExistingProperty_thenUpdatedPropertyWithoutAffectingOtherProperties() throws IOException {
        assertEquals("name=TestApp", propertyMutator.getPropertyKeyWithValue(1));

        int updatedPropertyIndex = propertyMutator.updateProperty("version=1.0", "version=2.0");

        assertEquals("version=2.0", propertyMutator.getPropertyKeyWithValue(updatedPropertyIndex));
        assertEquals("name=TestApp", propertyMutator.getPropertyKeyWithValue(1));
    }
}
