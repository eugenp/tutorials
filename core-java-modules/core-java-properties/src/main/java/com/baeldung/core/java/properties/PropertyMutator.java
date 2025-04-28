package com.baeldung.core.java.properties;

import java.io.IOException;

public interface PropertyMutator {

    String getProperty(String key) throws IOException;

    void addProperty(String key, String value) throws IOException;

    void updateProperty(String key, String value) throws IOException;
}
