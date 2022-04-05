package com.baeldung.properties.reloading.beans;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfigBean {

    private Properties properties;

    public PropertiesConfigBean(@Autowired Properties properties) {
        this.properties = properties;
    }

    public String getColor() {
        return properties.getProperty("application.theme.color");
    }
}
