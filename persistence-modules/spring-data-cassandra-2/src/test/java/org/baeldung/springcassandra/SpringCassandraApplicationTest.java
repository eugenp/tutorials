package org.baeldung.springcassandra;

import org.baeldung.springcassandra.config.CassandraContainerSingleton;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class SpringCassandraApplicationTest {

    @Container
    public static final CassandraContainerSingleton cassandra = CassandraContainerSingleton.getInstance();

    @Test
    void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
        assertThat(cassandra.isRunning()).isTrue();
    }

}
