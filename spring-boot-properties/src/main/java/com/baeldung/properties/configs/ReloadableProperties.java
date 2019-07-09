package com.baeldung.properties.configs;

import org.apache.commons.configuration.PropertiesConfiguration;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.util.Properties;

public class ReloadableProperties extends Properties {
    private PropertiesConfiguration propertiesConfiguration;

    public ReloadableProperties(PropertiesConfiguration propertiesConfiguration) throws IOException {
        super.load(new FileReader(propertiesConfiguration.getFile()));
        this.propertiesConfiguration = propertiesConfiguration;
    }

    @Override
    public synchronized Object setProperty(String key, String value) {
        propertiesConfiguration.setProperty(key, value);
        return super.setProperty(key, value);
    }

    @Override
    public String getProperty(String key) {
        String val = propertiesConfiguration.getString(key);
        super.setProperty(key, val);
        return val;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String val = propertiesConfiguration.getString(key, defaultValue);
        super.setProperty(key, val);
        return val;
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        throw new PropertiesException(new OperationNotSupportedException());
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        throw new PropertiesException(new OperationNotSupportedException());
    }

}
