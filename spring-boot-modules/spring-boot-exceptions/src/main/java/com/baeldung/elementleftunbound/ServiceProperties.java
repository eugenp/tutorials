package com.baeldung.elementleftunbound;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example.service")
public class ServiceProperties {

    private int timeout;

    // Uncomment this code to see property mismatch in section 4
    //private int timeOut;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    // Uncomment this code to see property mismatch in section 4
//    public int getTimeOut() {
//        return timeOut;
//    }
//
//    public void setTimeOut(int timeOut) {
//        this.timeOut = timeOut;
//    }
}

