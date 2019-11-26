package com.baeldung.patterns.hexagonal.domain;

import lombok.Data;

import java.io.Serializable;

@Data //Getter, Setter and Constructor with lombok
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    private String model;

    private String[] features;
}