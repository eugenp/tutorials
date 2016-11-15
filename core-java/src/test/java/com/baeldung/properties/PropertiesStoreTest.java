package com.baeldung.properties;

import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeClass;

import java.io.*;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PropertiesStoreTest {
    private static Properties appProps = new Properties();

    @Before
    public void before() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";

        // load the original properties
        appProps.load(new FileInputStream(appConfigPath));

        // update property list
        appProps.setProperty("name", "NewAppName");
        appProps.setProperty("downloadAddr", "www.baeldung.com/downloads");
    }

    @Test
    public void testStoreToPropertiesFile() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String newAppConfigPropertiesFile = rootPath + "newApp.properties";
        appProps.store(new FileWriter(newAppConfigPropertiesFile), "store to properties file");


        Properties verifyProps = new Properties();
        verifyProps.load(new FileInputStream(newAppConfigPropertiesFile));
        assertThat(verifyProps.getProperty("name"), equalTo("NewAppName"));
    }

    @Test
    public void testStoreToXmlFile() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String newAppConfigXmlFile = rootPath + "newApp.xml";
        appProps.storeToXML(new FileOutputStream(newAppConfigXmlFile), "store to xml file");

        Properties verifyProps = new Properties();
        verifyProps.loadFromXML(new FileInputStream(newAppConfigXmlFile));
        assertThat(verifyProps.getProperty("downloadAddr"), equalTo("www.baeldung.com/downloads"));
    }
}
