package com.baeldung.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PropertiesOperationsTest {
    private Properties appProps;

    @Before
    public void beforeClass() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
    }

    @Test
    public void testGetProperties() {
        String appVersion = appProps.getProperty("version");
        String appName = appProps.getProperty("name", "defaultName");
        String appGroup = appProps.getProperty("group", "baeldung");
        String appDownloadAddr = appProps.getProperty("downloadAddr");

        assertThat(appVersion, equalTo("1.0"));
        assertThat(appName, equalTo("TestApp"));
        assertThat(appGroup, equalTo("baeldung"));
        assertThat(appDownloadAddr, equalTo(null));
    }

    @Test
    public void testSetProperties() {
        appProps.setProperty("name", "NewAppName");
        appProps.setProperty("downloadAddr", "www.baeldung.com/downloads");

        String appName = appProps.getProperty("name");
        String appDownloadAddr = appProps.getProperty("downloadAddr");

        assertThat(appName, equalTo("NewAppName"));
        assertThat(appDownloadAddr, equalTo("www.baeldung.com/downloads"));
    }

    @Test
    public void testRemoveProperties() {
        String versionBeforeRemoval = appProps.getProperty("version");
        assertThat(versionBeforeRemoval, equalTo("1.0"));

        appProps.remove("version");

        String versionAfterRemoval = appProps.getProperty("version");
        assertThat(versionAfterRemoval, equalTo(null));
    }

    @Test
    public void testGetEnumerationOfValues() {
        Enumeration<Object> valueEnumeration = appProps.elements();
        List<String> values = Lists.newArrayList();
        while (valueEnumeration.hasMoreElements()) {
            String val = String.valueOf(valueEnumeration.nextElement());
            values.add(val);
        }
        assertArrayEquals(new String[] {"1.0", "2016-11-12", "TestApp"}, values.toArray());
    }

    @Test
    public void testGetEnumerationOfKeys() {
        Enumeration<Object> keyEnumeration = appProps.keys();
        List<String> keys = Lists.newArrayList();
        while (keyEnumeration.hasMoreElements()) {
            String key = String.valueOf(keyEnumeration.nextElement());
            keys.add(key);
        }
        assertArrayEquals(new String[] {"version", "date", "name"}, keys.toArray());
    }

    @Test
    public void testGetSize() {
        int size = appProps.size();
        assertThat(size, equalTo(3));
    }
}
