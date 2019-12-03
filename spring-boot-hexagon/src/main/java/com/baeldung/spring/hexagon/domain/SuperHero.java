package com.baeldung.spring.hexagon.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SuperHero {
    @Id
    @GeneratedValue
    private int id;
    
    private String name;
    private Type type;
    private int power;
}
