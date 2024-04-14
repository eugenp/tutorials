package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithjakartaannotation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityWithJakartaAnnotation {
    @Id
    private Long id;
}
