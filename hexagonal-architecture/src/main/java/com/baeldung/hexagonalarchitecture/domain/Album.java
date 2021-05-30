package com.baeldung.hexagonalarchitecture.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Album {
    
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    //...Standard Getters and Setters
}
