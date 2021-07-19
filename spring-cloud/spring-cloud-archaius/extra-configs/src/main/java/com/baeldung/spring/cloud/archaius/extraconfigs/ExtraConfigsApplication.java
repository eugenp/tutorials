package com.baeldung.spring.cloud.archaius.extraconfigs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExtraConfigsApplication {

    public static void main(String[] args) {
        // System properties can be set as command line arguments too
        System.setProperty("archaius.configurationSource.additionalUrls", "classpath:other-config-dir/extra.properties");
        System.setProperty("archaius.configurationSource.defaultFileName", "other.properties");
        SpringApplication.run(ExtraConfigsApplication.class, args);
    }

}
