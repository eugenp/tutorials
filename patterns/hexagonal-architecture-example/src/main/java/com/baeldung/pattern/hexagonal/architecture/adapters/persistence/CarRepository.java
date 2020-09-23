package com.baeldung.pattern.hexagonal.architecture.adapters.persistence;

import com.baeldung.pattern.hexagonal.architecture.application.domain.Car;
import com.baeldung.pattern.hexagonal.architecture.application.port.outgoing.LoadCarPort;
import com.baeldung.pattern.hexagonal.architecture.application.port.outgoing.SaveCarPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarRepository implements LoadCarPort, SaveCarPort {

    private SpringDataCarRepository springDataCarRepository;

    public CarRepository(SpringDataCarRepository springDataCarRepository) {
        this.springDataCarRepository = springDataCarRepository;
    }

    @Override
    public Optional<Car> load(Long id) {
        return springDataCarRepository.findById(id);
    }

    @Override
    public void save(Car car) {
        springDataCarRepository.save(car);
    }
}
