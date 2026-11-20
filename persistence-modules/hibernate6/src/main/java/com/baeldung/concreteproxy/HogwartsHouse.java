package com.baeldung.concreteproxy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import org.hibernate.annotations.ConcreteProxy;

@Entity
@ConcreteProxy
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class HogwartsHouse {

    @Id
    @GeneratedValue
    private Long id;

    private String founder;

    private String houseColors;

    HogwartsHouse() {
    }

    HogwartsHouse(String founder, String houseColors) {
        this.founder = founder;
        this.houseColors = houseColors;
    }

    Long getId() {
        return id;
    }

    String getHouseColors() {
        return houseColors;
    }

    void setHouseColors(String houseColors) {
        this.houseColors = houseColors;
    }

    String getFounder() {
        return founder;
    }

    void setFounder(String founder) {
        this.founder = founder;
    }
}