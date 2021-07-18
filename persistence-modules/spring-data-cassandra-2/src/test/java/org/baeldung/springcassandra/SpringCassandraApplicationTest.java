package org.baeldung.springcassandra;

import org.baeldung.springcassandra.config.CassandraContainerInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = SpringCassandraApplicationTest.Initializer.class)
@EnableConfigurationProperties
public class SpringCassandraApplicationTest extends CassandraContainerInitializer {

    @Test
    void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
        Assertions.assertTrue(cassandra.isRunning());
    }

}