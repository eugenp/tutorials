package com.baeldung.spring.notamanagedtype.jakartaannotation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityWithJakartaAnnotation {
    @Id
    private Long id;
}
