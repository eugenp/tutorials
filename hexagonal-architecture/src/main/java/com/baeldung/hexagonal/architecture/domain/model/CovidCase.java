package com.baeldung.hexagonal.architecture.domain.model;

import java.util.Objects;

/**
 * The class defines the domain object model
 */
public class CovidCase {

    private Integer id;
    private String patientName;
    private Integer age;
    private String city;

    public CovidCase() {
        super();
    }

    public CovidCase(Integer id, String patientName, Integer age, String city) {
        this.id = id;
        this.patientName = patientName;
        this.age = age;
        this.city = city;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CovidCase covidCase = (CovidCase) o;
        return (id == covidCase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
