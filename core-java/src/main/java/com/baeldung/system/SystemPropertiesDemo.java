package com.baeldung.system;

import java.util.HashMap;
import java.util.Properties;

public class SystemPropertiesDemo {
    public static void main(String[] args) {
        Properties properties = System.getProperties();

        // access each property one by one
        for (HashMap.Entry<Object, Object> value: properties.entrySet()) {
            System.out.println(value.getKey() + " = " + value.getValue());
        }

        // access a particular property using key
        System.out.println(System.getProperty("java.vm.vendor")); // OR
        System.out.println(properties.getProperty("sun.desktop"));

        // set a particular property using key
        System.setProperty("abckey", "abcvaluefoo");
        // read if property was set
        System.out.println(System.getProperty("abckey"));
        System.clearProperty("abckey");
        System.out.println(System.getProperty("abckey")); // prints null
    }
}
