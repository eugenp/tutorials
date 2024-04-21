package com.baeldung.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.inheritance.model.Car;
import com.baeldung.inheritance.model.dto.CarDTO;

@Mapper()
public interface CarMapper {

    CarDTO carToDTO(Car car);
}
