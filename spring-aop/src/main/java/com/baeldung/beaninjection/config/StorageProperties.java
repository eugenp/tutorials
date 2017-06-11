package com.baeldung.beaninjection.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smatt on 12/05/2017.
 */
@Configuration
public class StorageProperties {
    /**
     * Folder location for storing files
     */

    Logger logger = Logger.getLogger(StorageProperties.class);

    private String location = "upload-dir";

    public StorageProperties() {}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
