package org.baeldung.springcassandra.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class CassandraContainerInitializer {

    private static final String KEYSPACE_NAME = "test";

    @Container
    public static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:3.11.2")
      .withExposedPorts(9042);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            cassandra.start();
            createKeyspace(cassandra.getCluster());

            TestPropertyValues.of(
              "spring.data.cassandra.keyspace-name=" + KEYSPACE_NAME,
              "spring.data.cassandra.contact-points=" + cassandra.getContainerIpAddress(),
              "spring.data.cassandra.port=" + cassandra.getMappedPort(9042)
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    private static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
              "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

}