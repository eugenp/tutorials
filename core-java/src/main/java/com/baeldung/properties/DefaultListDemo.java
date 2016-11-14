package com.baeldung.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DefaultListDemo {
    public static void main(String[] args) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String defaultConfigPath = rootPath + "default.properties";
        String appConfigPath = rootPath + "app.properties";

        Properties defaultProps = new Properties();
        defaultProps.load(new FileInputStream(defaultConfigPath));

        Properties appProps = new Properties(defaultProps);
        appProps.load(new FileInputStream(appConfigPath));

        System.out.println(appProps.getProperty("name"));
        System.out.println(appProps.getProperty("version"));
        System.out.println(appProps.getProperty("site"));
    }
}
