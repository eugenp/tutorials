package com.baeldung.concreteproxy;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
class Wizard {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private HogwartsHouse house;

    Wizard() {
    }

    Wizard(String name, HogwartsHouse house) {
        this.name = name;
        this.house = house;
    }

    Long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    HogwartsHouse getHouse() {
        return house;
    }

    void setHouse(HogwartsHouse house) {
        this.house = house;
    }
}