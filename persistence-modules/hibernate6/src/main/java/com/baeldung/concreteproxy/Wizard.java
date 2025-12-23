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
    private HogwartsHouse hogwartsHouse;

    Wizard() {
    }

    Wizard(String name, HogwartsHouse hogwartsHouse) {
        this.name = name;
        this.hogwartsHouse = hogwartsHouse;
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

    HogwartsHouse getHogwartsHouse() {
        return hogwartsHouse;
    }

    void setHogwartsHouse(HogwartsHouse hogwartsHouse) {
        this.hogwartsHouse = hogwartsHouse;
    }
}