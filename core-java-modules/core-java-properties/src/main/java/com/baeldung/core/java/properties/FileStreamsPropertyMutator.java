package com.baeldung.core.java.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileStreamsPropertyMutator implements PropertyMutator {

    private final String propertyFileName;
    private final PropertyLoader propertyLoader;

    public FileStreamsPropertyMutator(String propertyFileName, PropertyLoader propertyLoader) {
        this.propertyFileName = propertyFileName;
        this.propertyLoader = propertyLoader;
    }

    @Override
    public String getProperty(String key) throws IOException {
        Properties properties = propertyLoader.fromFile(propertyFileName);

        return properties.getProperty(key);
    }

    @Override
    public void addOrUpdateProperty(String key, String value) throws IOException {
        Properties properties = propertyLoader.fromFile(propertyFileName);
        properties.setProperty(key, value);

        FileOutputStream out = new FileOutputStream(propertyLoader.getFilePathFromResources(propertyFileName));
        properties.store(out, null);
        out.close();
    }
}
