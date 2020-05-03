package com.baeldung.servlets.props;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

public final class Constants {

    @Autowired
    PropertySourcesLoader psl;

    public static final String breakLine = System.getProperty("line.separator");
    private static final PropertyLoader pl = new PropertyLoader();
    private static final Properties mainProps = pl.getProperties("custom.properties");
    public static final String DISPATCHER_SERVLET_NAME = mainProps.getProperty("dispatcher.servlet.name");
    public static final String DISPATCHER_SERVLET_MAPPING = mainProps.getProperty("dispatcher.servlet.mapping");
    private final String EXAMPLE_SERVLET_NAME = psl.getProperty("example.servlet.name");
    private final String EXAMPLE_SERVLET_MAPPING = psl.getProperty("example.servlet.mapping");

}
