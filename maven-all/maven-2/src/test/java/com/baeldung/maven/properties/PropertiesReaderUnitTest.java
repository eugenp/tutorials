package com.baeldung.maven.properties;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link PropertiesReader}.
 * 
 * @author Donato Rimenti
 */
public class PropertiesReaderUnitTest {

    /**
     * Reads a property and checks that's the one expected.
     * 
     * @throws IOException if anything goes wrong
     */
    @Test
    public void givenPomProperties_whenPropertyRead_thenPropertyReturned() throws IOException {
        PropertiesReader reader = new PropertiesReader("properties-from-pom.properties");
        String property = reader.getProperty("my.awesome.property");
        Assert.assertEquals("property-from-pom", property);
    }

}