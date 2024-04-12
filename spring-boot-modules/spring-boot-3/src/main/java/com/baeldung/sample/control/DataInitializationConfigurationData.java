package com.baeldung.sample.control;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.data")
@Data
public class DataInitializationConfigurationData {

    private boolean initializeOnStartup = true;

}
