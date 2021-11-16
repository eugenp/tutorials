package com.baeldung.h2db.springboot.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "countries")
@Entity
@Data
public class Country {

    @Id
    @GeneratedValue
    private int id;

    private String name;

}
