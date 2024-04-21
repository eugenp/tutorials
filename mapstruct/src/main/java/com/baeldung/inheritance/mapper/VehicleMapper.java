package com.baeldung.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.inheritance.model.Vehicle;
import com.baeldung.inheritance.model.dto.VehicleDTO;

@Mapper(uses = { CarMapper.class, BusMapper.class })
public interface VehicleMapper {

    VehicleDTO vehicleToDTO(Vehicle vehicle);
}
