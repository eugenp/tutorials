package com.baeldung.configuration.processor;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class PropertyBeanInjection {

    private final CustomProperties customProperties;

    PropertyBeanInjection(@Autowired CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    String getUrl() {
        return customProperties.getUrl();
    }

    int getTimeoutInMilliseconds() {
        return customProperties.getTimeoutInMilliSeconds();
    }
}
