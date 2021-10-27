package org.baeldung.hex.domain;


import org.baeldung.hex.domain.dto.Car;
import org.baeldung.hex.domain.entity.CarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarEntity domainToEntity(Car car);
    Car entityToDomain(CarEntity carEntity);
}
