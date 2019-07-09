package com.baeldung.properties.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.dynamic")
@RefreshScope
public class ConfigurationPropertiesRefreshConfigBean {
    private String prop1;

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp1() {
        return prop1;
    }
}
