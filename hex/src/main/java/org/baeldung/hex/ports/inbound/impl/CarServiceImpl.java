package org.baeldung.hex.ports.inbound.impl;

import lombok.RequiredArgsConstructor;
import org.baeldung.hex.util.NotFoundException;
import org.baeldung.hex.domain.CarMapper;
import org.baeldung.hex.domain.dto.Car;
import org.baeldung.hex.domain.entity.CarEntity;
import org.baeldung.hex.ports.inbound.CarService;
import org.baeldung.hex.ports.outbound.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public void addCar(Car car) {
        CarEntity carEntity = carMapper.domainToEntity(car);
        carRepository.save(carEntity);
    }

    @Override
    public Car getCar(String vinNumber) {
        Optional<CarEntity> optional = carRepository.findByVinNumber(vinNumber);
        if (optional.isPresent()) {
            CarEntity carEntity = optional.get();
            return carMapper.entityToDomain(carEntity);
        } else {
            throw new NotFoundException(String.format("No car found with vin: %s", vinNumber));
        }
    }

}
