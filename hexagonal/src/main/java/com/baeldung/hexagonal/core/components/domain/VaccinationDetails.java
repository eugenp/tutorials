package com.baeldung.hexagonal.core.components.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class VaccinationDetails {
    @Id
    private int vaccinationId;
    private String personId;
    private Date firstDose;
    private Boolean firstDoseDone = Boolean.FALSE;
    private Date secondDose;
    private Boolean secondDoseDone = Boolean.FALSE;
    private String centerDetails;
    private Date created;
    private Date lastModified;
}
