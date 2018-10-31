package com.baeldung.classpathloading;

import java.io.IOException;
import java.util.Properties;

public class ResourceLoading {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(ResourceLoading.class.getClassLoader().getResourceAsStream("com/baeldung/propsdemo/names.properties"));
        System.out.println(prop.getProperty("charname"));
    }
}
