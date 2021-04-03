package com.baeldung.hexagonal.core.ports.inbound;

import lombok.Data;

import java.util.Date;

@Data
public class CreateVaccinationRequest {
    private String personId;
    private Date firstDoseDate;
    private Date secondDoseDate;

}
