package com.baeldung.toggle;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.user.NoOpUserProvider;
import org.togglz.core.context.StaticFeatureManagerProvider;


@TestConfiguration
public class TestTogglzConfig {
    @Bean
    public FeatureManager featureManager() {
        FeatureManager manager = new FeatureManagerBuilder()
            .featureEnum(MyFeatures.class)
            .stateRepository(new InMemoryStateRepository())
            .userProvider(new NoOpUserProvider())
            .build();
        StaticFeatureManagerProvider.setFeatureManager(manager);
        return manager;
    }
}