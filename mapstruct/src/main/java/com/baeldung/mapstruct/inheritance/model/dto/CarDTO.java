package com.baeldung.mapstruct.inheritance.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CarDTO extends VehicleDTO {

    private Integer tires;

}
