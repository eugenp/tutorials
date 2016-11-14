package com.baeldung.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class StoreDemo {
    public static void main(String[] args) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";

        // load the original properties
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        // update property list
        appProps.setProperty("name", "NewAppName");
        appProps.setProperty("downloadAddr", "www.baeldung.com/downloads");

        // store new property list in properties file
        String newAppConfigPropertiesFile = rootPath + "newApp.properties";
        appProps.store(new FileWriter(newAppConfigPropertiesFile), "store to properties file");

        // store new property list in XML file
        String newAppConfigXmlFile = rootPath + "newApp.xml";
        appProps.storeToXML(new FileOutputStream(newAppConfigXmlFile), "store to xml file");
    }
}
