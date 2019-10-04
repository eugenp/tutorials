package com.baeldung.unmappedproperties.mapper;

import com.baeldung.unmappedproperties.dto.CarDTO;
import com.baeldung.unmappedproperties.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO carToCarDTO(Car car);
}