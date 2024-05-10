package com.baeldung.mapstruct.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;

@Mapper()
public interface CarMapper {

    CarDTO carToDTO(Car car);
}
