package com.baeldung.storedprocedure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;

@Entity
@NamedStoredProcedureQuery(name = "Car.getTotalCardsbyModelEntity", procedureName = "GET_TOTAL_CARS_BY_MODEL", parameters = {
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "model_in", type = String.class),
    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "count_out", type = Integer.class) })

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String model;

    @Column
    private Integer year;

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

}
