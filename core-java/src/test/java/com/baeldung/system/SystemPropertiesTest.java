package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Properties;

public class SystemPropertiesTest {

    @Test
    public void givenSystem_whenCalledGetProperties_thenReturnPropertiesinResult() {
        Properties properties = System.getProperties();

        // access each property one by one
        for (HashMap.Entry<Object, Object> value: properties.entrySet()) {
            System.out.println(value.getKey() + " = " + value.getValue());

            Assert.assertNotNull(value.getKey());
            Assert.assertNotNull(value.getValue());
        }

        // access a particular property using key
        System.out.println(System.getProperty("java.vm.vendor")); // OR
        System.out.println(properties.getProperty("sun.desktop"));

        Assert.assertNotNull(System.getProperty("java.vm.vendor"));
        Assert.assertNotNull(properties.getProperty("sun.desktop"));

        // set a particular property using key
        System.setProperty("abckey", "abcvaluefoo");
        // read if property was set
        System.out.println(System.getProperty("abckey"));

        Assert.assertNotNull(System.getProperty("abckey"));

        System.clearProperty("abckey");
        System.out.println(System.getProperty("abckey")); // prints null

        Assert.assertNull(System.getProperty("abckey"));
    }
}
