package com.baeldung.pattern.hexagonal.architecture;

import com.baeldung.pattern.hexagonal.architecture.adapters.persistence.CarRepository;
import com.baeldung.pattern.hexagonal.architecture.application.domain.Car;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HexagonalArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureApplication.class, args);
    }

    @Bean
    public CommandLineRunner bootstrapData(CarRepository carRepository) {
        return (args) -> {
            Car car = new Car();
            car.setId(1L);
            car.setInUse(false);
            carRepository.save(car);
        };
    }
}
