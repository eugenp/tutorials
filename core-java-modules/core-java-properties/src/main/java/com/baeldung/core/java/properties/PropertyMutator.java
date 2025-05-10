package com.baeldung.core.java.properties;

import java.io.IOException;

import org.apache.commons.configuration2.ex.ConfigurationException;

public interface PropertyMutator {

    String getProperty(String key) throws IOException, ConfigurationException;

    void addOrUpdateProperty(String key, String value) throws IOException, ConfigurationException;
}
