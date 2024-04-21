package com.baeldung.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.inheritance.model.Bus;
import com.baeldung.inheritance.model.dto.BusDTO;
import com.baeldung.inheritance.model.Car;
import com.baeldung.inheritance.model.dto.CarDTO;
import com.baeldung.inheritance.model.Vehicle;
import com.baeldung.inheritance.model.dto.VehicleDTO;

@Mapper()
public interface VehicleMapperByInstanceChecks {

    default VehicleDTO mapToVehicleDTO(Vehicle vehicle) {
        if (vehicle instanceof Bus) {
            return map((Bus) vehicle);
        } else if (vehicle instanceof Car) {
            return map((Car) vehicle);
        } else {
            return null;
        }
    }

    CarDTO map(Car car);

    BusDTO map(Bus bus);
}


