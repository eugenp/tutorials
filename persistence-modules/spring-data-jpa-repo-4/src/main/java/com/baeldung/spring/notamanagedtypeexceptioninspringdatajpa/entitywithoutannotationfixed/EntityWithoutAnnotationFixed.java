package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithoutannotationfixed;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityWithoutAnnotationFixed {
    @Id
    private Long id;
}
