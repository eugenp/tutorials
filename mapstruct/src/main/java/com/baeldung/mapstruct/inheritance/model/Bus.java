package com.baeldung.mapstruct.inheritance.model;

import com.baeldung.mapstruct.inheritance.model.dto.VehicleDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Bus extends Vehicle {

    private Integer capacity;

    @Override
    public VehicleDTO accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
