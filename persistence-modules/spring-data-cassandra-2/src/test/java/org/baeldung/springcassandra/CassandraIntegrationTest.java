package org.baeldung.springcassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.springcassandra.model.Car;
import org.baeldung.springcassandra.repository.CarRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = CassandraIntegrationTest.Initializer.class)
@EnableConfigurationProperties
class CassandraIntegrationTest {

    private static final String KEYSPACE_NAME = "test";

    @Container
    public static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:3.11.2")
      .withExposedPorts(9042);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
              "spring.data.cassandra.keyspace-name=" + KEYSPACE_NAME,
              "spring.data.cassandra.contact-points=" + cassandra.getContainerIpAddress(),
              "spring.data.cassandra.port=" + cassandra.getMappedPort(9042)
            ).applyTo(configurableApplicationContext.getEnvironment());

            createKeyspace(cassandra.getCluster());
        }
    }

    private static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
              "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Nested
    class SpringCassandraApplicationTest {

        @Test
        void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
            assertThat(cassandra.isRunning()).isTrue();
        }

    }

    @Nested
    class CarRepositoryIntegrationTest {

        @Autowired
        private CarRepository carRepository;

        @Test
        public void givenValidCarRecord_whenSavingIt_thenDataIsPersisted() {
            UUID carId = UUIDs.timeBased();
            final Car newCar = new Car(carId, "Nissan", "Qashqai", 2018);
            carRepository.save(newCar);

            List<Car> savedCars = carRepository.findAllById(List.of(carId));
            assertThat(savedCars.get(0)).isEqualTo(newCar);
        }

    }

}