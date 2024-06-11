package com.baeldung.spring.notamanagedtype.missedentityscan.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CorrectEntity {
    @Id
    private Long id;
}
