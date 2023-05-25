package com.baeldung.tasksservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisConfig implements ArtemisConfigurationCustomizer {
    @Value("${spring.artemis.host}")
    private String hostname;

    @Value("${spring.artemis.port}")
    private int port;

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        try {
            configuration.addAcceptorConfiguration("remote", "tcp://" + hostname + ":" + port);
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure Artemis listener", e);
        }
    }
}
