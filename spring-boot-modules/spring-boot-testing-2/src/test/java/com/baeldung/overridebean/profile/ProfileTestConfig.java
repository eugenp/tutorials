package com.baeldung.overridebean.profile;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.baeldung.overridebean.Service;

@TestConfiguration
public class ProfileTestConfig {

    @Bean
    @Profile("stub")
    public Service helloWorldStub() {
        return new ProfileServiceStub();
    }

    @Bean
    @Profile("mock")
    public Service helloWorldMock() {
        return mock(Service.class);
    }
}
