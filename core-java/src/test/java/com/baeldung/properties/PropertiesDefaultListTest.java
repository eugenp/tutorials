package com.baeldung.properties;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PropertiesDefaultListTest {
    @Test
    public void testDefaultList() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String defaultConfigPath = rootPath + "default.properties";
        String appConfigPath = rootPath + "app.properties";

        Properties defaultProps = new Properties();
        defaultProps.load(new FileInputStream(defaultConfigPath));

        Properties appProps = new Properties(defaultProps);
        appProps.load(new FileInputStream(appConfigPath));

        assertThat(appProps.getProperty("name"), equalTo("TestApp"));
        assertThat(appProps.getProperty("version"), equalTo("1.0"));
        assertThat(appProps.getProperty("site"), equalTo("www.google.com"));
    }
}
