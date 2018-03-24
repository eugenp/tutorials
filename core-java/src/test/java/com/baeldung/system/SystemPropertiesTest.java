package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class SystemPropertiesTest {

    @Test
    public void givenSystem_whenCalledGetProperties_thenReturnPropertiesinResult() {
        Assert.assertNotNull(System.getProperty("java.vm.vendor"));

        // set a particular property
        System.setProperty("abckey", "abcvaluefoo");
        Assert.assertEquals("abcvaluefoo", System.getProperty("abckey"));

        // Delete a property
        System.clearProperty("abckey");
        Assert.assertNull(System.getProperty("abckey"));

        System.clearProperty("dbHost");
        String myKey = System.getProperty("dbHost", "db.host.com");
        Assert.assertEquals("db.host.com", myKey);

        Properties properties = System.getProperties();

        Assert.assertNotNull(properties);

        // Clears all system properties. Use with care!
        System.getProperties().clear();

        Assert.assertTrue(System.getProperties().isEmpty());
    }
}
