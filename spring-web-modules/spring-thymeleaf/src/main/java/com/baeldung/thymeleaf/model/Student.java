package com.baeldung.thymeleaf.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * Simple student POJO with few fields
 *
 */
public class Student implements Serializable {

    private static final long serialVersionUID = -8582553475226281591L;

    @NotNull(message = "Student ID is required.")
    @Min(value = 1000, message = "Student ID must be at least 4 digits.")
    private Integer id;

    @NotNull(message = "Student name is required.")
    private String name;

    @NotNull(message = "Student gender is required.")
    private Character gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private Float percentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
}
