package com.baeldung.list;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.list.entity.Car;
import com.baeldung.list.entity.CarDto;
import com.baeldung.list.entity.ManufacturingPlantDto;

public abstract class CarMapperDecorator implements CustomCarMapper {
    private final Logger logger = LoggerFactory.getLogger(CarMapperDecorator.class);
    private CustomCarMapper delegate;

    public CarMapperDecorator(CustomCarMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public CarDto carToCarDto(Car car) {
        logger.info("calling Mapper decorator");
        CarDto carDto = delegate.carToCarDto(car);

        carDto.setManufacturingPlantDtos(getManufacturingPlantDtos(car));

        return carDto;
    }

    private List<ManufacturingPlantDto> getManufacturingPlantDtos(Car car) {
        // some custom logic or transformation which may require calls to other services
        return Arrays.asList(
            new ManufacturingPlantDto(car.getPlant1(), car.getPlant1Loc()),
            new ManufacturingPlantDto(car.getPlant2(), car.getPlant2Loc())
        );
    }
}
