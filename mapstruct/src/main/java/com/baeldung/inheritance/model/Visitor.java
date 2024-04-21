package com.baeldung.inheritance.model;

import com.baeldung.inheritance.model.dto.VehicleDTO;

public interface Visitor {

    VehicleDTO visit(Car car);

    VehicleDTO visit(Bus bus);
}
