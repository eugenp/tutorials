package com.baeldung.core.java.properties;

import java.io.IOException;

import org.apache.commons.configuration2.ex.ConfigurationException;

public interface PropertyMutator {

    String getProperty(String key) throws IOException, ConfigurationException;

    void addProperty(String key, String value) throws IOException, ConfigurationException;

    void updateProperty(String key, String value) throws IOException, ConfigurationException;
}
