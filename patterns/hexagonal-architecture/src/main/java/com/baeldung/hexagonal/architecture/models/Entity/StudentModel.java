package com.baeldung.hexagonal.architecture.models.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
public class StudentModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private String batch;
    private Integer registration_Number;

    public StudentModel(String name, String email, String batch, Integer registrationNumber) {
        this.name = name;
        this.email = email;
        this.batch = batch;
        this.registration_Number = registrationNumber;
    }

    public StudentModel() {
    }
}
