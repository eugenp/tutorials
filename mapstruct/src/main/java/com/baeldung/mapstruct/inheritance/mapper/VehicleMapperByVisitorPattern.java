package com.baeldung.mapstruct.inheritance.mapper;

import org.mapstruct.Mapper;

import com.baeldung.mapstruct.inheritance.model.Bus;
import com.baeldung.mapstruct.inheritance.model.dto.BusDTO;
import com.baeldung.mapstruct.inheritance.model.Car;
import com.baeldung.mapstruct.inheritance.model.dto.CarDTO;
import com.baeldung.mapstruct.inheritance.model.Vehicle;
import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;
import com.baeldung.mapstruct.inheritance.model.Visitor;

@Mapper()
public abstract class VehicleMapperByVisitorPattern implements Visitor {

    public VehicleDTO mapToVehicleDTO(Vehicle vehicle) {
        return vehicle.accept(this);
    }

    @Override
    public VehicleDTO visit(Car car) {
        return map(car);
    }

    @Override
    public VehicleDTO visit(Bus bus) {
        return map(bus);
    }

    abstract CarDTO map(Car car);

    abstract BusDTO map(Bus bus);
}
