package org.baeldung.config;

import java.io.IOException;

import org.baeldung.reddit.util.MyFeatures;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.UserProvider;

@Configuration
public class FeatureToggleConfig implements TogglzConfig {

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return MyFeatures.class;
    }

    @Override
    public StateRepository getStateRepository() {
        try {
            return new FileBasedStateRepository(new ClassPathResource("features.properties").getFile());
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserProvider getUserProvider() {
        return null;
    }

}