package com.baeldung.list;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.baeldung.list.entity.Car;
import com.baeldung.list.entity.CarDto;

@Mapper
@DecoratedWith(CarMapperDecorator.class)
public interface CustomCarMapper {
    CustomCarMapper INSTANCE = Mappers.getMapper(CustomCarMapper.class);

    @Mapping(source = "seats", target = "numberOfSeats")
    CarDto carToCarDto(Car car);
}
