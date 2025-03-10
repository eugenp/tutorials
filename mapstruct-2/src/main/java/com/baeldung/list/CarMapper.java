package com.baeldung.list;

import java.util.Arrays;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.baeldung.list.entity.Car;
import com.baeldung.list.entity.CarDto;
import com.baeldung.list.entity.ManufacturingPlantDto;

@Mapper(imports = { Arrays.class, ManufacturingPlantDto.class })
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(target = "numberOfSeats", source = "seats")
    @Mapping(
        target = "manufacturingPlantDtos",
        expression = """
            java(Arrays.asList(
            new ManufacturingPlantDto(car.getPlant1(), car.getPlant1Loc()),
            new ManufacturingPlantDto(car.getPlant2(), car.getPlant2Loc())
            ))
        """
    )
    CarDto carToCarDto(Car car);
}
