package com.baeldung.system;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;

public class SystemPropertiesUnitTest {

    @Test
    public void givenSystem_whenCalledGetProperty_thenReturnPropertyinResult() {
        Assert.assertNotNull(System.getProperty("java.vm.vendor"));
    }

    @Test
    public void givenSystem_whenCalledSetProperty_thenSetPropertyasResult() {

        // set a particular property
        System.setProperty("abckey", "abcvaluefoo");
        Assert.assertEquals("abcvaluefoo", System.getProperty("abckey"));
    }

    @Test
    public void givenSystem_whenCalledClearProperty_thenDeletePropertyasResult() {

        // Delete a property
        System.clearProperty("abckey");
        Assert.assertNull(System.getProperty("abckey"));
    }

    @Test
    public void givenSystem_whenCalledGetPropertyDefaultValue_thenReturnPropertyinResult() {

        System.clearProperty("dbHost");
        String myKey = System.getProperty("dbHost", "db.host.com");
        Assert.assertEquals("db.host.com", myKey);
    }

    @Test
    public void givenSystem_whenCalledGetProperties_thenReturnPropertiesinResult() {
        Properties properties = System.getProperties();

        Assert.assertNotNull(properties);
    }

    @Test
    @Ignore
    public void givenSystem_whenCalledClearProperties_thenDeleteAllPropertiesasResult() {

        // Clears all system properties. Use with care!
        System.getProperties().clear();

        Assert.assertTrue(System.getProperties().isEmpty());
    }
}
