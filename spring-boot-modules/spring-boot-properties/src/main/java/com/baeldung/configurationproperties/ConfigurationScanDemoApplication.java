package com.baeldung.configurationproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.baeldung.configurationproperties")
public class ConfigurationScanDemoApplication{

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationScanDemoApplication.class, args);
    }
}
