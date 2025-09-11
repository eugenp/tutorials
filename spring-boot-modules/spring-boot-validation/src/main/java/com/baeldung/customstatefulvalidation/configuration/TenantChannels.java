package com.baeldung.customstatefulvalidation.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.baeldung.tenant")
public class TenantChannels {
    private String[] channels;

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }
}
