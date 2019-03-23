package com.baeldung.properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;

public class MergePropertiesUnitTest {

    @Test
    public void givenTwoProperties_whenMergedUsingIteration_thenAllPropertiesInResult() {
        Properties globalProperties = mergePropertiesByIteratingKeySet(propertiesA(), propertiesB());

        testMergedProperties(globalProperties);
    }

    @Test
    public void givenTwoProperties_whenMergedUsingPutAll_thenAllPropertiesInResult() {
        Properties globalProperties = mergePropertiesByUsingPutAll(propertiesA(), propertiesB());

        testMergedProperties(globalProperties);
    }

    @Test
    public void givenTwoProperties_whenMergedUsingStreamAPI_thenAllPropertiesInResult() {
        Properties globalProperties = mergePropertiesByUsingStreamApi(propertiesB(), propertiesA());

        testMergedProperties(globalProperties);
    }

    private Properties mergePropertiesByIteratingKeySet(Properties... properties) {
        Properties mergedProperties = new Properties();
        for (Properties property : properties) {
            Set<String> propertyNames = property.stringPropertyNames();
            for (String name : propertyNames) {
                String propertyValue = property.getProperty(name);
                mergedProperties.setProperty(name, propertyValue);
            }
        }
        return mergedProperties;
    }

    private Properties mergePropertiesByUsingPutAll(Properties... properties) {
        Properties mergedProperties = new Properties();
        for (Properties property : properties) {
            mergedProperties.putAll(property);
        }
        return mergedProperties;
    }

    private Properties mergePropertiesByUsingStreamApi(Properties... properties) {
        return Stream.of(properties)
            .collect(Properties::new, Map::putAll, Map::putAll);
    }

    private Properties propertiesA() {
        Properties properties = new Properties();
        properties.setProperty("application.name", "my-app");
        properties.setProperty("application.version", "1.0");
        return properties;
    }

    private Properties propertiesB() {
        Properties properties = new Properties();
        properties.setProperty("property-1", "sample property");
        properties.setProperty("property-2", "another sample property");
        return properties;
    }

    private void testMergedProperties(Properties globalProperties) {
        assertThat("There should be 4 properties", globalProperties.size(), equalTo(4));
        assertEquals("Property should be", globalProperties.getProperty("application.name"), "my-app");
        assertEquals("Property should be", globalProperties.getProperty("application.version"), "1.0");
        assertEquals("Property should be", globalProperties.getProperty("property-1"), "sample property");
        assertEquals("Property should be", globalProperties.getProperty("property-2"), "another sample property");
    }

}
