package com.baeldung.mapstruct.inheritance.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusDTO extends VehicleDTO {

    private Integer capacity;

}
