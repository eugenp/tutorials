package com.baeldung.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class OperationsDemo {
    public static void main(String[] args) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        // retrieve values
        String appVersion = appProps.getProperty("version");
        String appName = appProps.getProperty("name", "defaultName");
        String appGroup = appProps.getProperty("group", "baeldung");
        String appDownloadAddr = appProps.getProperty("downloadAddr");
        System.out.println(appVersion);
        System.out.println(appName);
        System.out.println(appGroup);
        System.out.println(appDownloadAddr);

        // set values
        appProps.setProperty("name", "NewAppName");
        appProps.setProperty("downloadAddr", "www.baeldung.com/downloads");
        appName = appProps.getProperty("name");
        appDownloadAddr = appProps.getProperty("downloadAddr");
        System.out.println("new app name: " + appName);
        System.out.println("new app downloadAddr: " + appDownloadAddr);

        // remove a key-value pair
        System.out.println("before removal, version is: " + appProps.getProperty("version"));
        appProps.remove("version");
        System.out.println("after removal, version is: " + appProps.getProperty("version"));

        // list all key-value pairs
        appProps.list(System.out);

        // get an enumeration of all values
        Enumeration<Object> valueEnumeration = appProps.elements();
        while (valueEnumeration.hasMoreElements()) {
            System.out.println(valueEnumeration.nextElement());
        }

        // get an enumeration of all keys
        Enumeration<Object> keyEnumeration = appProps.keys();
        while (keyEnumeration.hasMoreElements()) {
            System.out.println(keyEnumeration.nextElement());
        }

        // get the count of key-value pairs
        int size = appProps.size();
        System.out.println(size);
    }
}
