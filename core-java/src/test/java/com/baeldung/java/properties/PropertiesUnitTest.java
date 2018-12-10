package com.baeldung.java.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

public class PropertiesUnitTest {

    @Test
    public void givenPropertyValue_whenPropertiesFileLoaded_thenCorrect() throws IOException {
     
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        String catalogConfigPath = rootPath + "catalog";
        
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
         
        Properties catalogProps = new Properties();
        catalogProps.load(new FileInputStream(catalogConfigPath));
        
        String appVersion = appProps.getProperty("version");
        assertEquals("1.0", appVersion);
        
        assertEquals("files", catalogProps.getProperty("c1"));
    }
    
    @Test
    public void givenPropertyValue_whenXMLPropertiesFileLoaded_thenCorrect() throws IOException {
     
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String iconConfigPath = rootPath + "icons.xml";
        Properties iconProps = new Properties();
        iconProps.loadFromXML(new FileInputStream(iconConfigPath));
        
        assertEquals("icon1.jpg", iconProps.getProperty("fileIcon"));
    }
    
    @Test
    public void givenAbsentProperty_whenPropertiesFileLoaded_thenReturnsDefault() throws IOException {
    
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        
        String appVersion = appProps.getProperty("version");
        String appName = appProps.getProperty("name", "defaultName");
        String appGroup = appProps.getProperty("group", "baeldung");
        String appDownloadAddr = appProps.getProperty("downloadAddr");
        
        assertEquals("1.0", appVersion);
        assertEquals("TestApp", appName);
        assertEquals("baeldung", appGroup);
        assertNull(appDownloadAddr);
    }
    
    @Test(expected = Exception.class)
    public void givenImproperObjectCasting_whenPropertiesFileLoaded_thenThrowsException() throws IOException {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        
        float appVerFloat = (float) appProps.get("version");
    }
    
    @Test
    public void givenPropertyValue_whenPropertiesSet_thenCorrect() throws IOException {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        
        appProps.setProperty("name", "NewAppName");
        appProps.setProperty("downloadAddr", "www.baeldung.com/downloads");
        
        String newAppName = appProps.getProperty("name");
        assertEquals("NewAppName", newAppName);
        
        String newAppDownloadAddr = appProps.getProperty("downloadAddr");
        assertEquals("www.baeldung.com/downloads", newAppDownloadAddr);
    }
    
    @Test
    public void givenPropertyValueNull_whenPropertiesRemoved_thenCorrect() throws IOException {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        
        String versionBeforeRemoval = appProps.getProperty("version");
        assertEquals("1.0", versionBeforeRemoval);

        appProps.remove("version");    
        String versionAfterRemoval = appProps.getProperty("version");
        assertNull(versionAfterRemoval);
    }
    
    @Test
    public void whenPropertiesStoredInFile_thenCorrect() throws IOException {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        
        String newAppConfigPropertiesFile = rootPath + "newApp.properties";
        appProps.store(new FileWriter(newAppConfigPropertiesFile), "store to properties file");
        
        String newAppConfigXmlFile = rootPath + "newApp.xml";
        appProps.storeToXML(new FileOutputStream(newAppConfigXmlFile), "store to xml file");
    }
    
    @Test
    public void givenPropertyValueAbsent_LoadsValuesFromDefaultProperties() throws IOException {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        
        String defaultConfigPath = rootPath + "default.properties";
        Properties defaultProps = new Properties();
        defaultProps.load(new FileInputStream(defaultConfigPath));
         
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties(defaultProps);
        appProps.load(new FileInputStream(appConfigPath));
         
        String appName = appProps.getProperty("name");        
        String appVersion = appProps.getProperty("version");
        String defaultSite = appProps.getProperty("site");
        
        assertEquals("1.0", appVersion);
        assertEquals("TestApp", appName);
        assertEquals("www.google.com", defaultSite);
    }

    @Test
    public void givenPropertiesSize_whenPropertyFileLoaded_thenCorrect() throws IOException {
        
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appPropsPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appPropsPath));
         
        appProps.list(System.out); // list all key-value pairs
        
        Enumeration<Object> valueEnumeration = appProps.elements();
        while (valueEnumeration.hasMoreElements()) {
            System.out.println(valueEnumeration.nextElement());
        }
         
        Enumeration<Object> keyEnumeration = appProps.keys();
        while (keyEnumeration.hasMoreElements()) {
            System.out.println(keyEnumeration.nextElement());
        }
         
        int size = appProps.size();
        assertEquals(3, size);
    }
}
