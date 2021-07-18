package org.baeldung.springcassandra.repository;

import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.springcassandra.config.CassandraContainerInitializer;
import org.baeldung.springcassandra.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = CarRepositoryIntegrationTest.Initializer.class)
@EnableConfigurationProperties
public class CarRepositoryIntegrationTest extends CassandraContainerInitializer {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void givenValidCarRecord_whenSavingIt_thenDataIsPersisted() {
        UUID carId = UUIDs.timeBased();
        final Car newCar = Car.builder()
          .id(carId)
          .make("Nissan")
          .model("Qashqai")
          .year(2018)
          .build();
        carRepository.save(newCar);

        List<Car> savedCars = carRepository.findAllById(List.of(carId));
        assertThat(savedCars.get(0)).isEqualTo(newCar);
    }

}
