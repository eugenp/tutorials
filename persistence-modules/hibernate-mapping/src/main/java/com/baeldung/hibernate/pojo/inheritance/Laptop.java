package com.baeldung.hibernate.pojo.inheritance;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class Laptop implements Item {
    
    @Id
    private Long id;

    private String type;

    public Laptop(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
