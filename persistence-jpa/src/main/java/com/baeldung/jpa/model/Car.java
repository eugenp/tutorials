package com.baeldung.jpa.model;

import javax.persistence.*;

/**
 * Created by Giuseppe Bueti on 22/02/2016.
 */

@Entity
@Table(name = "CAR")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "findByModelProcedure", procedureName = "FIND_CAR_BY_MODEL", resultClasses = { Car.class }, parameters = { @StoredProcedureParameter(name = "data", type = Car.class, mode = ParameterMode.REF_CURSOR),
                @StoredProcedureParameter(name = "p_model", type = String.class, mode = ParameterMode.IN) }),
        @NamedStoredProcedureQuery(name = "findByYearProcedure", procedureName = "FIND_CAR_BY_YEAR", resultClasses = { Car.class }, parameters = { @StoredProcedureParameter(name = "data", type = Car.class, mode = ParameterMode.REF_CURSOR),
                @StoredProcedureParameter(name = "p_year", type = Integer.class, mode = ParameterMode.IN) }) })
public class Car {

    private long id;
    private String model;
    private Integer year;

    public Car(String model, Integer year) {
        this.model = model;
        this.year = year;
    }

    public Car() {
    }

    @Id
    @SequenceGenerator(name = "CarIdSequence", sequenceName = "SEQ_CAR_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CarIdSequence")
    // @GeneratedValue(strategy = GenerationType.IDENTITY) -- for MySQL
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "YEAR")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
