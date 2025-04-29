package com.baeldung.core.java.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class XMLFilePropertyMutatorUnitTest {

    Properties initialProperties = new Properties();
    XMLFilePropertyMutator propertyMutator = new XMLFilePropertyMutator("icons.xml");

    @BeforeEach
    public void setUp() throws IOException {
        String filepath = propertyMutator.getXMLAppPropertiesWithFileStreamFilePath();
        try (InputStream is = Files.newInputStream(Paths.get(filepath))) {
            initialProperties.loadFromXML(is);
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        String filepath = propertyMutator.getXMLAppPropertiesWithFileStreamFilePath();
        try (OutputStream os = Files.newOutputStream(Paths.get(filepath))) {
            initialProperties.storeToXML(os, null);
        }
    }

    @Test
    public void addProperty_whenXMLPropertyFile_thenReturnsNewPropertyWithoutAffectingOtherProperties() throws IOException {
        assertEquals("icon1.jpg", propertyMutator.getProperty("fileIcon"));
        assertNull(propertyMutator.getProperty("new.property"));

        propertyMutator.addProperty("new.property", "new-value");

        assertEquals("new-value", propertyMutator.getProperty("new.property"));
        assertEquals("icon1.jpg", propertyMutator.getProperty("fileIcon"));
    }

    @Test
    public void updateProperty_whenXMLPropertyFile_thenReturnsUpdatedPropertyWithoutAffectingOtherProperties() throws IOException {
        assertEquals("icon1.jpg", propertyMutator.getProperty("fileIcon"));
        assertEquals("icon2.jpg", propertyMutator.getProperty("imageIcon"));

        propertyMutator.updateProperty("fileIcon", "icon5.jpg");

        assertEquals("icon5.jpg", propertyMutator.getProperty("fileIcon"));
        assertEquals("icon2.jpg", propertyMutator.getProperty("imageIcon"));
    }
}
