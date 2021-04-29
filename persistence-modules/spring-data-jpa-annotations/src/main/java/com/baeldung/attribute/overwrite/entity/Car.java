package com.baeldung.attribute.overwrite.entity;


import javax.persistence.*;
import java.util.Map;

@Entity
@AttributeOverride(name = "identifier", column = @Column(name = "VIN"))
public class Car extends Vehicle {

    private String model;
    private String name;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "BRAND_NAME", length = 5)),
            @AttributeOverride(name = "address.name", column = @Column(name = "ADDRESS_NAME"))
    })
    private Brand brand;
    @ElementCollection
    @AttributeOverrides({
            @AttributeOverride(name = "key.name", column = @Column(name = "OWNER_NAME")),
            @AttributeOverride(name = "key.surname", column = @Column(name = "OWNER_SURNAME")),
            @AttributeOverride(name = "value.name", column = @Column(name = "ADDRESS_NAME")),
    })
    Map<Owner, Address> owners;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
