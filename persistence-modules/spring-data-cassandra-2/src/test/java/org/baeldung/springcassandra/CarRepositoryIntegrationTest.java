package org.baeldung.springcassandra;

import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.springcassandra.config.CassandraContainerSingleton;
import org.baeldung.springcassandra.model.Car;
import org.baeldung.springcassandra.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class CarRepositoryIntegrationTest {

    @Container
    public static final CassandraContainerSingleton cassandra = CassandraContainerSingleton.getInstance();

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