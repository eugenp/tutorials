package com.baeldung.spring.notamanagedtype.missedannotation;

import javax.persistence.Id;

public class EntityWithoutAnnotation {
    @Id
    private Long id;
}
