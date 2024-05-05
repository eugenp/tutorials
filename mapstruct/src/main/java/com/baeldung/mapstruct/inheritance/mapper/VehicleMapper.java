package com.baeldung.mapstruct.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.mapstruct.inheritance.model.Vehicle;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

@Mapper(uses = { CarMapper.class, BusMapper.class })
public interface VehicleMapper {

    VehicleDTO vehicleToDTO(Vehicle vehicle);
}
