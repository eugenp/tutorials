package com.baeldung.hexagonal.core.ports.inbound;

import lombok.Data;

@Data
public class CreateVaccinationResponse {
    private String personId;
    private Boolean firstDoseDone;
    private Boolean secondDoseDone;
}
