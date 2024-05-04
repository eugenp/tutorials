package com.baeldung.mapstruct.inheritance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.Vehicle;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

@Mapper()
public interface VehicleMapperBySubclassMapping {

    @SubclassMapping(source = Car.class, target = CarDTO.class)
    @SubclassMapping(source = Bus.class, target = BusDTO.class)
    VehicleDTO mapToVehicleDTO(Vehicle vehicle);
}
