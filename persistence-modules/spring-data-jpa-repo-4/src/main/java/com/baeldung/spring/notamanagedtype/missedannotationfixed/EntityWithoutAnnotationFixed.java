package com.baeldung.spring.notamanagedtype.missedannotationfixed;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityWithoutAnnotationFixed {
    @Id
    private Long id;
}
