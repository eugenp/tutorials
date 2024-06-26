package com.baeldung.mapstruct.inheritance.model;

import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

public interface Visitor {

    VehicleDTO visit(Car car);

    VehicleDTO visit(Bus bus);
}
