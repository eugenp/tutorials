package org.baeldung.springcassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.springcassandra.model.Car;
import org.baeldung.springcassandra.repository.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// This live test needs a running Docker instance so that a Cassandra container can be created

@Testcontainers
@SpringBootTest
class CassandraNestedLiveTest {

    private static final String KEYSPACE_NAME = "test";

    @Container
    private static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:3.11.2")
      .withExposedPorts(9042);

    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getContainerIpAddress());
        System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));

        createKeyspace(cassandra.getCluster());
    }

    static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
              "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Nested
    class ApplicationContextLiveTest {

        @Test
        void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
            assertThat(cassandra.isRunning()).isTrue();
        }

    }

    @Nested
    class CarRepositoryLiveTest {

        @Autowired
        private CarRepository carRepository;

        @Test
        void givenValidCarRecord_whenSavingIt_thenRecordIsSaved() {
            UUID carId = UUIDs.timeBased();
            Car newCar = new Car(carId, "Nissan", "Qashqai", 2018);

            carRepository.save(newCar);

            List<Car> savedCars = carRepository.findAllById(List.of(carId));
            assertThat(savedCars.get(0)).isEqualTo(newCar);
        }

        @Test
        void givenExistingCarRecord_whenUpdatingIt_thenRecordIsUpdated() {
            UUID carId = UUIDs.timeBased();
            Car existingCar = carRepository.save(new Car(carId, "Nissan", "Qashqai", 2018));

            existingCar.setModel("X-Trail");
            carRepository.save(existingCar);

            List<Car> savedCars = carRepository.findAllById(List.of(carId));
            assertThat(savedCars.get(0).getModel()).isEqualTo("X-Trail");
        }

        @Test
        void givenExistingCarRecord_whenDeletingIt_thenRecordIsDeleted() {
            UUID carId = UUIDs.timeBased();
            Car existingCar = carRepository.save(new Car(carId, "Nissan", "Qashqai", 2018));

            carRepository.delete(existingCar);

            List<Car> savedCars = carRepository.findAllById(List.of(carId));
            assertThat(savedCars.isEmpty()).isTrue();
        }

    }

}
