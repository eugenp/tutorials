package com.baeldung.hexagonalarchitecture2;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.common.GeneratedId;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

import java.util.UUID;

@Configuration
public class AppointmentsApplicationPersistenceConfig {

    @Bean
    public ApplicationListener<BeforeSaveEvent<Object>> idGenerator() {
        return event -> {
            var entity = event.getEntity();
            if (entity instanceof GeneratedId) {
                ((GeneratedId) entity).setId(UUID.randomUUID().toString());
            }
        };
    }
}
