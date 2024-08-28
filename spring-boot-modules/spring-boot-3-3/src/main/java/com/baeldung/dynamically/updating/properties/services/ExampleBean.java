package com.baeldung.dynamically.updating.properties.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class ExampleBean {

    @Value("${my.custom.property}")
    private String customProperty;

    public String getCustomProperty() {
        return customProperty;
    }
}
