package com.baeldung.jpa.removal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShipmentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public ShipmentInfo(String name) {
        this.name = name;
    }

    protected ShipmentInfo() {}
}
