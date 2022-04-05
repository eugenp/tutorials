package com.baeldung.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.CarDTO;
import com.baeldung.entity.Car;

@Mapper
public interface CarMapper {
    
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    
    CarDTO carToCarDTO(Car car);
}
