package com.baeldung.commons.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;
import org.junit.jupiter.api.Test;

class ConfigurationClassUnitTest {

    @Test
    void givenPropertiesFile_whenReadingWithConfigurationClass_thenIsLoaded() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("src/test/resources/configuration/file.properties"));
        String dbHost = config.getString("db.host");
        int dbPort = config.getInt("db.port");
        String dbUser = config.getString("db.user");
        String dbPassword = config.getString("undefinedKey", "defaultValue");
        assertEquals("baeldung.com", dbHost);
        assertEquals(9999, dbPort);
        assertEquals("admin", dbUser);
        assertEquals("defaultValue", dbPassword);
    }

    @Test
    void givenXMLFile_whenReadingWithConfigurationClass_thenIsLoaded() throws ConfigurationException {
        Configurations configs = new Configurations();
        XMLConfiguration config = configs.xml(new File("src/test/resources/configuration/hierarchical.xml"));
        String appender = config.getString("appender[@name]");
        List<String> encoderPatterns = config.getList(String.class, "appender.encoder.pattern");
        String pattern1 = config.getString("appender.encoder.pattern(0)");
        assertEquals("STDOUT", appender);
        assertEquals(2, encoderPatterns.size());
        assertEquals("Pattern1", pattern1);
    }

    @Test
    void givenPropertiesFile_whenCopyingConfiguration_thenIsSuccessful() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration baseConfig = configs.properties(new File("src/test/resources/configuration/file.properties"));
        Configuration subConfig = new PropertiesConfiguration();
        subConfig.addProperty("db.host", "baeldung");
        subConfig.addProperty("db.driver", "dummyDriver");
        ((AbstractConfiguration) subConfig).copy(baseConfig);
        String dbHost = subConfig.getString("db.host");
        String dbDriver = subConfig.getString("db.driver");
        int dbPort = subConfig.getInt("db.port");
        String dbUser = subConfig.getString("db.user");
        assertEquals("baeldung.com", dbHost);
        assertEquals(9999, dbPort);
        assertEquals("admin", dbUser);
        assertEquals("dummyDriver", dbDriver);
    }

    @Test
    public void givenPropertiesFile_whenAppendingConfiguration_thenIsSuccessful() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration baseConfig = configs.properties(new File("src/test/resources/configuration/file.properties"));
        Configuration subConfig = new PropertiesConfiguration();
        subConfig.addProperty("db.host", "baeldung");
        subConfig.addProperty("db.driver", "dummyDriver");
        ((AbstractConfiguration) subConfig).append(baseConfig);
        String dbHost = subConfig.getString("db.host");
        String dbDriver = subConfig.getString("db.driver");
        int dbPort = subConfig.getInt("db.port");
        String dbUser = subConfig.getString("db.user");
        assertEquals("baeldung", dbHost);
        assertEquals(9999, dbPort);
        assertEquals("admin", dbUser);
        assertEquals("dummyDriver", dbDriver);
    }

    @Test
    void givenXMLFile_whenCloningConfiguration_thenIsSuccessful() throws ConfigurationException {
        Configurations configs = new Configurations();
        XMLConfiguration baseConfig = configs.xml(new File("src/test/resources/configuration/hierarchical.xml"));
        XMLConfiguration subConfig = new XMLConfiguration();
        //subConfig = (XMLConfiguration) baseConfig.clone();
        subConfig = new XMLConfiguration(baseConfig);
        String appender = subConfig.getString("appender[@name]");
        List<String> encoderPatterns = subConfig.getList(String.class, "appender.encoder.pattern");
        String pattern1 = subConfig.getString("appender.encoder.pattern(0)");
        assertEquals("STDOUT", appender);
        assertEquals(2, encoderPatterns.size());
        assertEquals("Pattern1", pattern1);
    }

    @Test
    void givenEncodedProperty_whenCustomDecoderImplemented_thenIsSuccessful() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("src/test/resources/configuration/file.properties"));
        ((AbstractConfiguration) config).setConfigurationDecoder(new CustomDecoder());
        assertEquals("mySecretString", config.getEncodedString("db.password"));
    }

    @Test
    void whenDataTypeConversionAttempted_thenIsSuccessful() {
        Configuration config = new PropertiesConfiguration();
        config.addProperty("stringProperty", "This is a string");
        config.addProperty("numericProperty", "9999");
        config.addProperty("booleanProperty", "true");
        assertEquals("This is a string", config.getString("stringProperty"));
        assertEquals(9999, config.getInt("numericProperty"));
        assertTrue(config.getBoolean("booleanProperty"));
    }

    @Test
    void whenDataTypeConversionAttempted_thenThrowsException() {
        Configuration config = new PropertiesConfiguration();
        config.addProperty("numericProperty", "9999a");
        assertThrows(ConversionException.class, () -> config.getInt("numericProperty"));
    }

    @Test
    void whenInterpolationIsAttempted_thenIsSuccessful() throws ConfigurationException {
        System.setProperty("user.name", "Baeldung");
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("src/test/resources/configuration/file.properties"));
        String dbUrl = config.getString("db.url");
        String userName = config.getString("db.username");
        String externalService = config.getString("db.external-service");
        assertEquals("baeldung.com:9999", dbUrl);
        assertEquals("Baeldung", userName);
        assertEquals("https://www.baeldung.com", externalService);
    }

    @Test
    void whenDelimiterIsSpecified_thenMultiValuePropertyIsLoaded() {
        PropertiesConfiguration propertiesConfig = new PropertiesConfiguration();
        propertiesConfig.setListDelimiterHandler(new DefaultListDelimiterHandler(';'));
        propertiesConfig.addProperty("delimitedProperty", "admin;read-only;read-write");
        propertiesConfig.addProperty("arrayProperty", "value1;value2");
        List<Object> delimitedProperties = propertiesConfig.getList("delimitedProperty");
        String[] arrayProperties = propertiesConfig.getStringArray("arrayProperty");
        assertEquals(3, delimitedProperties.size());
        assertEquals("admin", delimitedProperties.get(0));
        assertEquals(2, arrayProperties.length);
        assertEquals("value1", propertiesConfig.getString("arrayProperty"));
    }

    @Test
    void whenPropertiesAreMissing_thenIsHandled() {
        PropertiesConfiguration propertiesConfig = new PropertiesConfiguration();
        String objectProperty = propertiesConfig.getString("anyProperty");
        int primitiveProperty = propertiesConfig.getInt("anyProperty", 1);
        assertNull(objectProperty);
        assertEquals(1, primitiveProperty);
    }

    @Test
    void whenPropertiesAreMissing_thenExceptionIsThrown() {
        PropertiesConfiguration propertiesConfig = new PropertiesConfiguration();
        assertThrows(NoSuchElementException.class, () -> propertiesConfig.getInt("anyProperty"));
    }

}
