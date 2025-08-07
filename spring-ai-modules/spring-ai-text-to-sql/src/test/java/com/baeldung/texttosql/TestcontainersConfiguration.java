package com.baeldung.texttosql;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    /**
     * Downgraded MySQL version since the container is unable to run when using the latest version.
     * The relevant bug in testcontainers can be tracked here: https://github.com/testcontainers/testcontainers-java/issues/10184
     */
    @Bean
    @ServiceConnection
    MySQLContainer mySQLContainer() {
        return new MySQLContainer("mysql:9.2.0");
    }

}