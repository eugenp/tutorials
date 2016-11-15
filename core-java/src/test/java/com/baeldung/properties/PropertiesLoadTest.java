package com.baeldung.properties;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoadTest {
    @Test
    public void testLoadFromPropertiesFiles() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        String catalogConfigPath = rootPath + "catalog";

        // load from properties file which use .properties as the suffix
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        // load from properties file which not use .properties as the suffix
        Properties catalogProps = new Properties();
        catalogProps.load(new FileInputStream(catalogConfigPath));
    }

    @Test
    public void testLoadFromXmlFile() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String iconConfigPath = rootPath + "icons.xml";
        Properties iconProps = new Properties();
        iconProps.loadFromXML(new FileInputStream(iconConfigPath));
    }
}
