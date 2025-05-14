package com.baeldung.core.java.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class PropertyLoader {

    public Properties fromFile(String filename) throws IOException {
        String appPropertiesFileName = getFilePathFromResources(filename);
        FileInputStream in = new FileInputStream(appPropertiesFileName);
        Properties properties = new Properties();

        properties.load(in);
        in.close();

        return properties;
    }

    public String getFilePathFromResources(String filename) {
        URL resourceUrl = getClass().getClassLoader()
          .getResource(filename);
        Objects.requireNonNull(resourceUrl, "Property file with name [" + filename + "] was not found.");

        return resourceUrl.getFile();
    }
}
