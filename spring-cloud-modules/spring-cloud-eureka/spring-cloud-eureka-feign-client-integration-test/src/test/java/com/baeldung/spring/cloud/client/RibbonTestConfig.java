package com.baeldung.spring.cloud.client;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.WireMockServer;

@TestConfiguration
@ActiveProfiles("ribbon-test")
public class RibbonTestConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBooksService() {
        return new WireMockServer(options().port(80));
    }

    @Bean(name="secondMockBooksService", initMethod = "start", destroyMethod = "stop")
    public WireMockServer secondBooksMockService() {
        return new WireMockServer(options().port(81));
    }
}
