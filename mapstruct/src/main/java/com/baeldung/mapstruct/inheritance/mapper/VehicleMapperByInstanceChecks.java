package com.baeldung.mapstruct.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.Vehicle;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

@Mapper()
public interface VehicleMapperByInstanceChecks {

    CarDTO map(Car car);

    BusDTO map(Bus bus);

    default VehicleDTO mapToVehicleDTO(Vehicle vehicle) {
        if (vehicle instanceof Bus) {
            return map((Bus) vehicle);
        } else if (vehicle instanceof Car) {
            return map((Car) vehicle);
        } else {
            return null;
        }
    }
}


