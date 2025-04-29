package com.baeldung.core.java.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class XMLFilePropertyMutator implements PropertyMutator {

    private final String propertyFileName;

    public XMLFilePropertyMutator(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    public String getProperty(String key) throws IOException {
        Properties properties = loadProperties();

        return properties.getProperty(key);
    }

    @Override
    public void addProperty(String key, String value) throws IOException {
        String filePath = getXMLAppPropertiesWithFileStreamFilePath();
        Properties properties = loadProperties(filePath);

        try (OutputStream os = Files.newOutputStream(Paths.get(filePath))) {
            properties.setProperty(key, value);
            properties.storeToXML(os, null);
        }
    }

    @Override
    public void updateProperty(String key, String value) throws IOException {
        String filePath = getXMLAppPropertiesWithFileStreamFilePath();
        Properties properties = loadProperties(filePath);

        try (OutputStream os = Files.newOutputStream(Paths.get(filePath))) {
            properties.setProperty(key, value);
            properties.storeToXML(os, null);
        }
    }

    private Properties loadProperties() throws IOException {
        return loadProperties(getXMLAppPropertiesWithFileStreamFilePath());
    }

    private Properties loadProperties(String filepath) throws IOException {
        Properties props = new Properties();
        try (InputStream is = Files.newInputStream(Paths.get(filepath))) {
            props.loadFromXML(is);
        }

        return props;
    }

    String getXMLAppPropertiesWithFileStreamFilePath() {
        URL resourceUrl = getClass().getClassLoader()
          .getResource(propertyFileName);
        Objects.requireNonNull(resourceUrl, "Property file with name [" + propertyFileName + "] was not found.");

        return resourceUrl.getFile();
    }
}
