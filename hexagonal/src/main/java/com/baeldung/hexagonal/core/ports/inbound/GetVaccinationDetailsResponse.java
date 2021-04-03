package com.baeldung.hexagonal.core.ports.inbound;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class GetVaccinationDetailsResponse {
    private int vaccinationId;
    private String personId;
    private Date firstDose;
    private Boolean firstDoseDone;
    private Date secondDose;
    private Boolean secondDoseDone;
    private String centerDetails;
}
