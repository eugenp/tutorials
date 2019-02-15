package com.baeldung.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "CAR")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "findByYearProcedure", procedureName = "FIND_CAR_BY_YEAR", resultClasses = { Car.class }, parameters = { @StoredProcedureParameter(name = "p_year", type = Integer.class, mode = ParameterMode.IN) }) })
public class Car {

    private long id;
    private String model;
    private Integer year;

    public Car(final String model, final Integer year) {
        this.model = model;
        this.year = year;
    }

    public Car() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    @Column(name = "YEAR")
    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }
}
