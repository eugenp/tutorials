package com.baeldung.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadDemo {
    public static void main(String[] args) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        String catalogConfigPath = rootPath + "catalog";
        String iconConfigPath = rootPath + "icons.xml";

        // load from properties file which use .properties as the suffix
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        // load from properties file which not use .properties as the suffix
        Properties catalogProps = new Properties();
        catalogProps.load(new FileInputStream(catalogConfigPath));

        // load from XML file
        Properties iconProps = new Properties();
        iconProps.loadFromXML(new FileInputStream(iconConfigPath));
    }
}
