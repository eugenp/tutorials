package com.baeldung.configuration.processor;

import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Configuration
@ConfigurationProperties(prefix = "com.baeldung")
public class CustomProperties {

    /**
     * The url to connect to.
     */
    String url;

    /**
     * The time to wait for the connection.
     */
    private int timeoutInMilliSeconds = 1000;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeoutInMilliSeconds() {
        return timeoutInMilliSeconds;
    }

    public void setTimeoutInMilliSeconds(int timeoutInMilliSeconds) {
        this.timeoutInMilliSeconds = timeoutInMilliSeconds;
    }

}
