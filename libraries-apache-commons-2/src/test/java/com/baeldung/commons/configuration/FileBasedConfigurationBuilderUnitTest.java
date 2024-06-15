package com.baeldung.commons.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration2.interpol.ExprLookup;
import org.apache.commons.configuration2.interpol.Lookup;
import org.junit.jupiter.api.Test;

class FileBasedConfigurationBuilderUnitTest {

    @Test
    void whenReadingPropertiesFile_thenIsSuccessful() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
            PropertiesConfiguration.class).configure(params.properties()
            .setFileName("src/test/resources/configuration/file1.properties"));
        Configuration config = builder.getConfiguration();
        String dbHost = config.getString("db.host");
        int dbPort = config.getInt("db.port");
        String dbUser = config.getString("db.user");
        assertEquals("baeldung.com", dbHost);
        assertEquals(9999, dbPort);
        assertEquals("admin", dbUser);
    }

    @Test
    void whenReadingXMLFile_thenIsSuccessful() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder = new FileBasedConfigurationBuilder<>(XMLConfiguration.class).configure(params.xml()
            .setFileName("src/test/resources/configuration/hierarchical.xml")
            .setValidating(true));
        XMLConfiguration config = builder.getConfiguration();
        String appender = config.getString("appender[@name]");
        List<String> encoderPatterns = config.getList(String.class, "appender.encoder.pattern");
        assertEquals("STDOUT", appender);
        assertEquals(2, encoderPatterns.size());
    }

    @Test
    void whenExpressionEvaluationIsAttempted_thenIsSuccessful() throws ConfigurationException {
        System.setProperty("user.home", "/usr/lib");
        Parameters params = new Parameters();
        Map<String, Lookup> lookups = new HashMap<>(ConfigurationInterpolator.getDefaultPrefixLookups());
        ExprLookup.Variables variables = new ExprLookup.Variables();
        variables.add(new ExprLookup.Variable("System", "Class:java.lang.System"));
        ExprLookup exprLookup = new ExprLookup(variables);
        exprLookup.setInterpolator(new ConfigurationInterpolator());
        lookups.put("expr", exprLookup);
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
            PropertiesConfiguration.class).configure(params.properties()
            .setFileName("src/test/resources/configuration/file1.properties")
            .setPrefixLookups(lookups));
        Configuration config = builder.getConfiguration();
        String dbDumpLocation = config.getString("db.data-dump-location");
        assertEquals("/usr/lib/dump.dat", dbDumpLocation);
    }

}
