package com.baeldung.libraries.debezium.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private Long id;

    private String fullname;
    private String email;
}
